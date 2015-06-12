package com.ciandt.gcc.tasks;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.ciandt.gcc.OfyService;
import com.ciandt.gcc.api.ContactsApi;
import com.ciandt.gcc.entities.User;
import com.ciandt.gcc.entities.Contact;
import com.googlecode.objectify.Objectify;

@SuppressWarnings("serial")
public class ImportationTask extends HttpServlet {

    private static final Logger log = Logger.getLogger(ImportationTask.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        Objectify ofy = OfyService.ofy();
        
        User user = ofy.load().type(User.class).id(req.getParameter("userEmail")).now();
        log.info("Task started for user: " + user.getEmail());
        
        String accessToken = user.getCredential().getAccessToken();    
        ContactsApi api = new ContactsApi(accessToken);
        List<Contact> contacts = api.getContacts();
        
        user.setContacts(contacts);
        user.setImported(true);
        ofy.save().entity(user).now();
    }
}
