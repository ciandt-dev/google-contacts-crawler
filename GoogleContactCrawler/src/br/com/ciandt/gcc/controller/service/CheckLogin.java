package br.com.ciandt.gcc.controller.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import br.com.ciandt.gcc.controller.model.Mail;


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
              
               
               Mail mailUser = new Mail();
               mailUser.setEndMail(user.getEmail());


               ValidateMail v = new ValidateMail();
               boolean validatedMail = v.validate(mailUser.getEndMail());
              
               
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
