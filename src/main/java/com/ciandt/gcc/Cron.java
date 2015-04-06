package com.ciandt.gcc;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.*;

@SuppressWarnings("serial")
public class Cron extends HttpServlet {
  
  private static final Logger log = Logger.getLogger(Cron.class.getName());
  
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      Filter propertyFilter =
          new FilterPredicate("imported",
                              FilterOperator.EQUAL,
                              false);
      Query q = new Query("User").setFilter(propertyFilter);
      List<Entity> results = datastore.prepare(q)
          .asList(FetchOptions.Builder.withDefaults());
      log.info("Queried for User entities pending importation.");
      
      for (Entity entity : results) {
        String user_email = entity.getKey().getName();
        log.info("User: " + user_email);
        Key key = entity.getKey();
        String serializedKey = KeyFactory.keyToString(key);
        // Add the task to the default queue.
        Queue queue = QueueFactory.getDefaultQueue();
        queue.add(TaskOptions.Builder.withUrl("/import")
            .param("user_email", user_email)
            .param("accessToken", (String) entity.getProperty("accessToken")) 
            .param("userKey", serializedKey)
            .method(Method.POST));
      }
  }
  
}
