package com.ciandt.gcc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class SendContacts extends HttpServlet{

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws IOException, ServletException {
        
        String reqUser = req.getParameter("contact");
        
        System.out.println(reqUser);
        
    }
}
