package com.ciandt.gcc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

@SuppressWarnings("serial")
public class SendContacts extends HttpServlet{

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws IOException, ServletException {
        
        String reqUser = req.getParameter("contact");
        String setMail = "@";
        
        
        if(reqUser.equals(null)){
            
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
        
        
       
        
    }
}
