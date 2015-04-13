package com.ciandt.gcc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.oauth.OAuthService;
import com.google.appengine.api.oauth.OAuthServiceFactory;

@SuppressWarnings("serial")
public class SendContacts extends HttpServlet{

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws IOException, ServletException {
        
        try {
            OAuthService oauth = OAuthServiceFactory.getOAuthService();
            com.google.appengine.api.users.User users = oauth.getCurrentUser();
            
            resp.getWriter().println(users.getEmail());
            
            String reqUser = req.getParameter("contact");
            String setMail = "@";
            
            if(reqUser == null){
                
                resp.getWriter().println("Null Parameter");
                
            }else{
                
                User user = new User();
                
                if(reqUser.contains(setMail)){

               try {
                   resp.getWriter().println(user.QueryContactsAncestor(reqUser));
               } catch (JSONException e) {
                    e.printStackTrace();
                }
                

                }else if(reqUser.equals("all")){
                    
                try {
                    resp.getWriter().println(user.QueryContacts());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                    
                }else{
                     
                    resp.getWriter().println("Invalid Parameter");
                }
                
            }
        
        
        } catch (OAuthRequestException e) {
            resp.getWriter().println("Not authenticated: " + e.getMessage());
        }
  
    }
}