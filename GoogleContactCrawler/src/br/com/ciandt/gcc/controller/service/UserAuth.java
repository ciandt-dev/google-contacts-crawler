package br.com.ciandt.gcc.controller.service;

import java.io.IOException;

import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.security.krb5.internal.Authenticator;
import sun.security.krb5.internal.AuthorizationData;
import br.com.ciandt.gcc.controller.model.Mail;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.apphosting.api.logservice.LogServicePb.UserAppLogGroup;
import com.google.apphosting.api.logservice.LogServicePb.UserAppLogLine;
import com.google.apphosting.utils.config.AppEngineWebXml.UseGoogleConnectorJ;
import com.google.gdata.data.appsforyourdomain.provisioning.UserEntry;

@SuppressWarnings("serial")
public class UserAuth extends HttpServlet{
    
    public void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws IOException,  ServletException{
        
       
        
        UserService userService = UserServiceFactory.getUserService();    
        User user = userService.getCurrentUser();
        
        resp.getWriter().println(user.getEmail());

        /*if (user != null) {
            
            
            user.getEmail(); // colocar algum código aqui ;)
            userService.createLogoutURL(req.getRequestURI());
            
        } else {
            
            userService.createLoginURL(req.getRequestURI());
        
        }*/

            
            /*UserService userService = UserServiceFactory.getUserService();
            User user = userService.getCurrentUser();
            
            resp.getWriter().println(user);
            
            String URI = "https://mail.google.com";
            resp.sendRedirect(userService.createLoginURL(URI));*/
    }
}
