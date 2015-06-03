package com.ciandt.gcc.tasks;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ciandt.gcc.PMF;
import com.ciandt.gcc.entities.User;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;
import com.google.appengine.api.datastore.*;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

@SuppressWarnings("serial")
public class Cron extends HttpServlet {
  
    private static final Logger log = Logger.getLogger(Cron.class.getName());
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws IOException, ServletException {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        
        Query query = pm.newQuery(User.class);
        query.setFilter("imported == " + req.getParameter("imported"));
        List<User> results = (List<User>) query.execute();
        
        if (results.isEmpty()) {
          pm.close();
          return;
        }
        
        for (User user : results) {
            String userEmail = user.getEmail();
            
            // Add the task to the default queue.
            Queue queue = QueueFactory.getDefaultQueue();
            queue.add(TaskOptions.Builder.withUrl("/import")
                .param("userEmail", userEmail)
                .method(Method.POST));
            
            log.info("Task enqueued for user : " + userEmail); 
        }
        
        pm.close();
    }
}
