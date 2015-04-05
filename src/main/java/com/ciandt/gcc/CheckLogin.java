package com.ciandt.gcc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;


@SuppressWarnings("serial")
public class CheckLogin extends HttpServlet {
    
   
    public void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws IOException,  ServletException{
        
       try{
           
           UserService userService = UserServiceFactory.getUserService();    
           User user = userService.getCurrentUser(); 
                      
            
           if(user == null){
                
               userService.createLoginURL(req.getRequestURI());
               
           }else{

               ValidateMail v = new ValidateMail();
               boolean validatedMail = v.validate(user.getEmail());
              
               
               if(validatedMail == true){
                   
                   String URI = "reference.html";
                    resp.sendRedirect(URI);
                   //resp.getWriter().println(mailUser.getEndMail());
                   
               }else{
                   
                   resp.getWriter().println("Não é um e-mail CIANDT");
               }
               
           }
           

            
        }catch(Exception e){
         
            System.out.println("Erro ao pegar e-mail");
        }
        
       
    }


}