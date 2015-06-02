package com.ciandt.gcc.auth;

import java.io.IOException;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ciandt.gcc.PMF;
import com.ciandt.gcc.entities.User;
import com.google.api.client.auth.oauth2.*;
import com.google.api.client.extensions.appengine.auth.oauth2.AbstractAppEngineAuthorizationCodeCallbackServlet;
import com.google.appengine.api.users.UserServiceFactory;

public class OAuth2Callback extends AbstractAppEngineAuthorizationCodeCallbackServlet {
  
    private static final long serialVersionUID = 1L;
  
    @Override
    protected void onSuccess(HttpServletRequest req, HttpServletResponse resp, Credential credential)
        throws ServletException, IOException {
        String email = UserServiceFactory.getUserService().getCurrentUser().getEmail();    
        PersistenceManager pm = PMF.get().getPersistenceManager();
        
        try {
            pm.getObjectById(User.class, email);
        } catch (JDOObjectNotFoundException exception) {
            User user = new User(email); 
            user.setCredential(credential);
            pm.makePersistent(user);
            pm.close();
        }
    }
  
    @Override
    protected void onError(
        HttpServletRequest req, HttpServletResponse resp, AuthorizationCodeResponseUrl errorResponse)
        throws ServletException, IOException {
        resp.setStatus(200);
        resp.addHeader("Content-Type", "text/html");
    }
  
    @Override
    protected String getRedirectUri(HttpServletRequest req) throws ServletException, IOException {
        return OAuthUtils.getRedirectUri(req);
    }
  
    @Override
    protected AuthorizationCodeFlow initializeFlow() throws IOException {
        return OAuthUtils.newFlow();
    }
}
