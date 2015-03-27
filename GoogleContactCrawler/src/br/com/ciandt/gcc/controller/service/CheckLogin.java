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
           
            
            Mail mailUser = new Mail();
            //mailUser.setEndMail(req.getParameter("mail"));
            mailUser.setEndMail("teste@ciandt.com");
            
            
            UserService userService = UserServiceFactory.getUserService();    
            User user = userService.getCurrentUser();

            
            String userMail = user.getEmail();
            
            
            resp.getWriter().println(userMail);
            
            
            /*if(mailUser.getEndMail() == ""){
             
                resp.getWriter().println("valor nulo");
                
            }else{

                
                ValidateMail v = new ValidateMail();
                boolean validatedMail = v.validate(mailUser.getEndMail());
                
                if(validatedMail == true){
                  
                    resp.getWriter().println("CIANDT");
                    
                }else{
                    
                    resp.getWriter().println("NÂO");
                }*/
                
            }

            
        }catch(Exception e){
         
            System.out.println("Erro ao pegar e-mail");
        }
        
       
    }


}
