package Controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.Mail;


@SuppressWarnings("serial")
public class CheckLogin extends HttpServlet {
    

    public void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws IOException,  ServletException{

        
        
       try{
           
            
            Mail mailUser = new Mail();
            //mailUser.setEndMail(req.getParameter("mail"));
            mailUser.setEndMail("teste@ciandt.com");
            
            
            if(mailUser.getEndMail() == ""){
             
                resp.getWriter().println("valor nulo");
                
            }else{

                
                ValidateMail v = new ValidateMail();
                boolean validatedMail = v.validate(mailUser.getEndMail());
                
                if(validatedMail == true){
                  
                    resp.getWriter().println("CIANDT");
                    
                }else{
                    
                    resp.getWriter().println("NÂO");
                }
                
            }

            
        }catch(Exception e){
         
            System.out.println("Erro ao pegar e-mail");
        }
        
       
    }


}
