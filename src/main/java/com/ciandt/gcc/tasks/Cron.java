package com.ciandt.gcc.tasks;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ciandt.gcc.OfyService;
import com.ciandt.gcc.entities.User;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;
import com.googlecode.objectify.Objectify;

@SuppressWarnings("serial")
public class Cron extends HttpServlet {
  
    private static final Logger log = Logger.getLogger(Cron.class.getName());
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws IOException, ServletException {
        Objectify ofy = OfyService.ofy();
        List<User> users = ofy.load().type(User.class).filter("imported", false).list();
        
        if (users.isEmpty()) {
          return;
        }
        
        for (User user : users) {
            String userEmail = user.getEmail();
            
            // Add the task to the default queue.
            Queue queue = QueueFactory.getDefaultQueue();
            queue.add(TaskOptions.Builder.withUrl("/import")
                .param("userEmail", userEmail)
                .method(Method.POST));
            
            log.info("Task enqueued for user : " + userEmail); 
        }
    }
}
