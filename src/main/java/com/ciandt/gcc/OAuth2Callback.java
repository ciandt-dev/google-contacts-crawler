package com.ciandt.gcc;

import java.io.IOException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.*;
import com.google.api.client.extensions.appengine.auth.oauth2.AbstractAppEngineAuthorizationCodeCallbackServlet;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.client.Query;
import com.google.gdata.data.contacts.ContactEntry;


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
    
    
    /*DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(user);*/
    
    ContactsService contactsService = new ContactsService("Lasso Project");
    contactsService.setHeader("Authorization", "Bearer " + credential.getAccessToken());
    
    
    try {
        
        URL feedUrl = new URL(FEED_URL_CONTACTS);
        
        Query myQuery = new Query(feedUrl);
        myQuery.setMaxResults(MAX_RESULTS);

        ContactFeed resultFeed = contactsService.query(myQuery, ContactFeed.class);

        for (ContactEntry entry : resultFeed.getEntries()) {

            System.out.println(entry.getTitle().getPlainText());
        
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

