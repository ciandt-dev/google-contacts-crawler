package com.ciandt.gcc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.*;
import com.google.api.client.extensions.appengine.auth.oauth2.AbstractAppEngineAuthorizationCodeCallbackServlet;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.UserServiceFactory;

public class OAuth2Callback extends AbstractAppEngineAuthorizationCodeCallbackServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void onSuccess(HttpServletRequest req, HttpServletResponse resp, Credential credential)
      throws ServletException, IOException {
    
    String email = UserServiceFactory.getUserService().getCurrentUser().getEmail();
    
    ValidateMail v = new ValidateMail();
    boolean validatedMail = v.validate(email);
    
    if(validatedMail == true){
        
        long expireTime = (System.currentTimeMillis() / 1000L) + credential.getExpiresInSeconds();
        
        Entity user = new Entity("User", email);
        user.setProperty("accessToken", credential.getAccessToken());
        user.setProperty("expireTime", expireTime);
        user.setProperty("refreshToken", credential.getRefreshToken());
        
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(user);
        
    }else{
        
        resp.getWriter().println("Não é um e-mail CIANDT");
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

