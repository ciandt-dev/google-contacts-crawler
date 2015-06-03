package com.ciandt.gcc.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.*;

@SuppressWarnings("serial")
@PersistenceCapable
public class User implements Serializable {
    @PrimaryKey
    private String email;
    
    @Persistent
    private boolean imported;

    @Persistent(dependent = "true")
    private Credential credential;
    
    @Persistent
    private List<Contact> contacts = new ArrayList<>();
  
    public User(String email) {
        this.setEmail(email);
    }

    public List<Contact> getContacts() {
        return contacts;
    }
    
    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
        this.imported = true;
    }

    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setCredential(com.google.api.client.auth.oauth2.Credential credential) {
        this.credential = new Credential(credential);    
    }

    public Credential getCredential() {
        return this.credential;
    }
}
