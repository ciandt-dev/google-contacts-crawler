package com.ciandt.gcc;

import java.io.IOException;
import java.net.URL;

import com.google.appengine.repackaged.com.google.protobuf.ServiceException;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.contacts.ContactFeed;

public class RetrievingContacts {

    public void PushContacts(ContactsService myContacts)
            throws ServiceException, IOException {
        
        URL feedUrl =  new URL("https://www.google.com/m8/feeds/contacts/default/full");
    
        try {
            
            ContactFeed resultFeed = myContacts.getFeed(feedUrl, ContactFeed.class);
            
            for (int i = 0; i < resultFeed.getEntries().size(); i++){
                
                System.out.println(i);
            }
        
        } catch (com.google.gdata.util.ServiceException e) {
            
            e.printStackTrace();
        }
    }

}
