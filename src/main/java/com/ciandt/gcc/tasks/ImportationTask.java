package com.ciandt.gcc.tasks;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.ciandt.gcc.PMF;
import com.ciandt.gcc.api.ContactsApi;
import com.ciandt.gcc.entities.User;
import com.ciandt.gcc.entities.Contact;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("serial")
public class ImportationTask extends HttpServlet {

    private static final Logger log = Logger.getLogger(ImportationTask.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        
        User user = pm.getObjectById(User.class, req.getParameter("userEmail"));
        log.info("Task started for user: " + user.getEmail());
        
        String accessToken = user.getCredential().getAccessToken();    
        ContactsApi api = new ContactsApi(accessToken);
        List<Contact> contacts = api.getContacts();
        
        user.setContacts(contacts);
        
        pm.close();
    }
}
