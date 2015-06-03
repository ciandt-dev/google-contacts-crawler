package com.ciandt.gcc.api;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.ciandt.gcc.ReaderPropertiesDomains;
import com.ciandt.gcc.entities.*;
import com.google.gdata.client.Query;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.data.extensions.Email;
import com.google.gdata.data.extensions.Name;

public class ContactsApi {
  
    private static final Integer MAX_RESULTS = 99999;
    private static final String CONTACTS_FEED_URL = "https://www.google.com/m8/feeds/contacts/default/full";
    
    private String accessToken;
    
    public ContactsApi(String accessToken) {
        this.accessToken = accessToken;
    }
    
    public List<Contact> getContacts() {
        String applicationName = "ciandt-google-contacts-crawler";
        List<Contact> contacts = new ArrayList<>();
        
        ContactsService contactsService = new ContactsService(applicationName);
        contactsService.getRequestFactory().setHeader("User-Agent", applicationName);
        contactsService.setHeader("Authorization", "Bearer " + accessToken);
  
        try {
            URL feedUrl = new URL(CONTACTS_FEED_URL);
  
            Query query = new Query(feedUrl);
            query.setMaxResults(MAX_RESULTS);
            ContactFeed resultFeed = contactsService.query(query, ContactFeed.class);
  
            contacts = processResponse(resultFeed);
        } catch (Exception e) {
            System.out.println(e);
        }
        return contacts;
    }

    private List<Contact> processResponse(ContactFeed resultFeed)
        throws IOException {
        List<Contact> contacts = new ArrayList<>();
        
        for (ContactEntry entry : resultFeed.getEntries()) {
            if (!entry.hasName()) {
                continue;
            }
   
            Name name = entry.getName();
   
            for (Email mail : entry.getEmailAddresses()) {
                if (this.domainIgnoredList(mail.getAddress())) {
                    continue;
                }
   
                Contact contact = new Contact(mail.getAddress());
                contact.setName(name.getFullName().getValue());
                contacts.add(contact);
            }
        }
        return contacts;
    }
    
    public boolean domainIgnoredList(String contact) throws IOException {
      for (String domains : ReaderPropertiesDomains.getProperties()) {
          if (contact.contains(domains)) {
              return true;
          }
      }
      return false;
    }
}
