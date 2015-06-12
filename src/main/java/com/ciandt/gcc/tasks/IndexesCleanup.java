package com.ciandt.gcc.tasks;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ciandt.gcc.OfyService;
import com.ciandt.gcc.entities.Contact;
import com.ciandt.gcc.entities.Credential;
import com.ciandt.gcc.entities.User;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.impl.QueryImpl;

public class IndexesCleanup extends HttpServlet {

    private static final long serialVersionUID = 4490926573554004555L;
    
    private static final Logger log = Logger.getLogger(ImportationTask.class.getName());
  
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        Objectify ofy = OfyService.ofy();

        List<User> users = ofy.load().type(User.class).list();
        for (User user : users) {
            ofy.save().entity(user).now();
        }
        
        List<Credential> credentials = ofy.load().type(Credential.class).list();
        for (Credential credential : credentials) {
          ofy.save().entity(credential).now();
        }
        
        Queue queue = QueueFactory.getDefaultQueue();
        queue.add(TaskOptions.Builder
            .withUrl("/indexes_cleanup")
            .method(Method.POST));
        
        log.info("Contacts indexes cleanup started assynchronously..."); 
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        Objectify ofy = OfyService.ofy();
        QueryImpl<Contact> query = (QueryImpl<Contact>) ofy.load().type(Contact.class).limit(1000);
  
        String cursorStr = req.getParameter("cursor");
        if (cursorStr != null)
            query = query.startAt(Cursor.fromWebSafeString(cursorStr));
  
        boolean continu = false;
        QueryResultIterator<Contact> iterator = query.iterator();
        while (iterator.hasNext()) {
            Contact contact = iterator.next();
            ofy.save().entity(contact).now();
            continu = true;
        }
  
        if (continu) {
            Cursor cursor = iterator.getCursor();
            Queue queue = QueueFactory.getDefaultQueue();
            queue.add(TaskOptions.Builder
                .withUrl("/indexes_cleanup")
                .param("cursor", cursor.toWebSafeString())
                .method(Method.POST));
        }
    }
   
}
