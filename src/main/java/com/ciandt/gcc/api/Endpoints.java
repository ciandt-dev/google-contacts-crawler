package com.ciandt.gcc.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

import com.ciandt.gcc.User;

@SuppressWarnings("serial")
public class SendContacts extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        String resKey = "123";
        String key = req.getHeader("Authorization");
        resp.setContentType("application/json");
        
        if(!key.equals(resKey)){
            
            resp.getWriter().println("Unauthorized");
        
        }else{
        
            String reqUser = req.getParameter("contact");
            String setMail = "@";

        if (reqUser == null) {

            resp.getWriter().println("Null Parameter");

        } else {

            User user = new User();

            if (reqUser.contains(setMail)) {

                try {
                    resp.getWriter().println(
                            user.QueryContactsAncestor(reqUser));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else if (reqUser.equals("all")) {

                try {
                    resp.getWriter().println(user.QueryContacts());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {

                resp.getWriter().println("Invalid Parameter");
            }

        }
    }
    }
}

