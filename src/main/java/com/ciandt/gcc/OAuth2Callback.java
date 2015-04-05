package com.ciandt.gcc;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.*;
import com.google.api.client.extensions.appengine.auth.oauth2.AbstractAppEngineAuthorizationCodeCallbackServlet;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.extensions.Name;
import com.google.gdata.data.extensions.Email;


public class OAuth2Callback extends AbstractAppEngineAuthorizationCodeCallbackServlet {

  private static final long serialVersionUID = 1L;
  private static final Integer MAX_RESULTS = 1000;
  private static final String FEED_URL_CONTACTS = "https://www.google.com/m8/feeds/contacts/default/full";
  
  protected void onSuccess(HttpServletRequest req, HttpServletResponse resp, Credential credential)
      throws ServletException, IOException {
    
    String email = UserServiceFactory.getUserService().getCurrentUser().getEmail();
    
    long expireTime = (System.currentTimeMillis() / 1000L) + credential.getExpiresInSeconds();
        
    Entity user = new Entity("User", email);
    user.setProperty("accessToken", credential.getAccessToken());
    user.setProperty("expireTime", expireTime);
    user.setProperty("refreshToken", credential.getRefreshToken());
    
    
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Key userKey = user.getKey();
    datastore.put(user);
    
    
    ContactsService contactsService = new ContactsService("Lasso Project");
    contactsService.setHeader("Authorization", "Bearer " + credential.getAccessToken());
    
    
    try {
        
        
        URL feedUrl = new URL(FEED_URL_CONTACTS);
        
        com.google.gdata.client.Query myQuery = new com.google.gdata.client.Query(feedUrl);
        myQuery.setMaxResults(MAX_RESULTS);

        ContactFeed resultFeed = contactsService.query(myQuery, ContactFeed.class);

        for (ContactEntry entry : resultFeed.getEntries()) {
            
            
            if(entry.hasName()){
                
                Name name = entry.getName();
                String fullName = name.getFullName().getValue();
                
                    if(name.getFullName().hasYomi()){
                        
                        fullName += " (" + name.getFullName().getYomi() + ")";
                        
                    }

                    
                    for (Email mail : entry.getEmailAddresses()) {


                        String contactMail = mail.getAddress();
                        String contactName = fullName;
                        

                        Entity contacts = new Entity("Contacts", contactMail, userKey);
                        contacts.setProperty("Name", contactName);

                        DatastoreService datastores = DatastoreServiceFactory.getDatastoreService();
                        datastores.put(contacts);
                    
                        //Example Query results mail contacts with related ancestor
                        
                        Query photoQuery = new Query("Contacts").setAncestor(userKey); 
                        
                        List<Entity> results = datastore.prepare(photoQuery).asList(FetchOptions.Builder.withDefaults());
                        
                        System.out.println(results);
                }
               
            
            }else{
                
                //System.out.println("Not FullName");
            }

        }    

    } catch (Exception e) {
        System.out.println(e);
    }
    
    
  }
    
  @Override
  protected void onError(
      HttpServletRequest req, HttpServletResponse resp, AuthorizationCodeResponseUrl errorResponse)
      throws ServletException, IOException {
    String nickname = UserServiceFactory.getUserService().getCurrentUser().getNickname();
    resp.getWriter().print("<h3>" + nickname + ", why don't you want to play with me?</h1>");
    resp.setStatus(200);
    resp.addHeader("Content-Type", "text/html");
  }

  @Override
  protected String getRedirectUri(HttpServletRequest req) throws ServletException, IOException {
    return Utils.getRedirectUri(req);
  }

  @Override
  protected AuthorizationCodeFlow initializeFlow() throws IOException {
    return Utils.newFlow();
  }
}

