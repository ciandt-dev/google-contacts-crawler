package com.ciandt.gcc.entities;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;

import static com.googlecode.objectify.ObjectifyService.ofy;

@Cache
@Entity
public class User {
    @Id String email;
    
    @Index boolean imported;
    
    @Load Ref<Credential> credential;
    
    List<Ref<Contact>> contacts = new ArrayList<Ref<Contact>>();
  
    @SuppressWarnings("unused")
    private User() {}
    
    public User(String email) {
        this.setEmail(email);
    }

    public List<Contact> getContacts() {
        return new ArrayList<Contact>(ofy().load().refs(contacts).values());
    }
    
    public void setContacts(List<Contact> contacts) {
        for (Contact contact : contacts) {
            ofy().save().entity(contact).now();
            this.contacts.add(Ref.create(contact));
        }
    }

    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setCredential(com.google.api.client.auth.oauth2.Credential credential) {
        Credential c = new Credential(credential);
        ofy().save().entity(c).now();
        this.credential = Ref.create(c);    
    }

    public Credential getCredential() {
        return this.credential.get();
    }

    public boolean isImported() {
      return imported;
    }

    public void setImported(boolean imported) {
      this.imported = imported;
    }
}
