package com.ciandt.gcc;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gdata.client.contacts.*;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.data.extensions.*;
import com.google.gdata.client.*;

@SuppressWarnings("serial")
public class ImportContacts extends HttpServlet {
  
  private static final Logger log = Logger.getLogger(Cron.class.getName());
  private static final Integer MAX_RESULTS = 99999;
  private static final String FEED_URL_CONTACTS = "https://www.google.com/m8/feeds/contacts/default/full";
  private static final String[] filterDomains = {"ciandt"};
  
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
      log.info("Enqueued task started for user: " + req.getParameter("user_email"));
      log.info("Parent Key: " + req.getParameter("userKey"));
      log.info("Access Token: " + req.getParameter("accessToken"));
      
      
      String accessToken = req.getParameter("accessToken");   
      String applicationName = "ciandt-google-contacts-crawler";
      ContactsService contactsService = new ContactsService(applicationName);
      contactsService.getRequestFactory().setHeader("User-Agent", applicationName);
      //contactsService.setUserToken(accessToken);
      contactsService.setHeader("Authorization", "Bearer " + accessToken);
           
      try {
          
          URL feedUrl = new URL(FEED_URL_CONTACTS);
          
          Query q = new Query(feedUrl);
          q.setMaxResults(MAX_RESULTS);
          ContactFeed resultFeed = contactsService.query(q, ContactFeed.class);

          for (ContactEntry entry : resultFeed.getEntries()) {
              if (!entry.hasName()){
                  continue;
              }
              
              Name name = entry.getName();
              String fullName = name.getFullName().getValue();
              Key userKey = KeyFactory.stringToKey(req.getParameter("userKey"));
              
              for (Email mail : entry.getEmailAddresses()) {
                  String contactAddress = mail.getAddress();
                  String contactName = fullName;

                  
                  if(this.domainIgnoredList(contactAddress)){
                      
                      if(true){
                          continue;
                      }
                  }
                  
                  
                  Entity contact = new Entity("Contact", contactAddress, userKey);
                  contact.setProperty("name", contactName);

                  DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
                  datastore.put(contact);
                  log.info("Inserted contact: " + contactAddress); 
                  
              }
          }   
      } catch (Exception e) {
          System.out.println(e);
      }
  }
  
  public boolean domainIgnoredList(String contacts){
      
      for (String domains : filterDomains) {
        
          if(contacts.contains(domains)){
             
              return true;
          }
      }
      
      return false;
      
    }
}
