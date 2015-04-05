package com.ciandt.gcc;

import java.io.IOException;
import java.net.URL;

import com.google.appengine.repackaged.com.google.protobuf.ServiceException;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.contacts.ContactFeed;

public class RetrievingContacts {

    public static void doGet(ContactsService contactService)
            throws ServiceException, IOException {

        try {

            URL feedURL = new URL("https://www.google.com/m8/feeds/contacts/default/full");
            ContactFeed resultFeed = contactService.getFeed(feedURL,ContactFeed.class);

            System.out.println(resultFeed.getTitle().getPlainText());
            
        } catch (com.google.gdata.util.ServiceException e) {

            e.printStackTrace();
        }
    }
}
