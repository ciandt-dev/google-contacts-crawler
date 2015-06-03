package com.ciandt.gcc.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ciandt.gcc.entities.Contact;

@SuppressWarnings("serial")
public class UserBean implements Serializable {
  private String email;
  
  private List<Contact> contacts = new ArrayList<>();

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public List<Contact> getContacts() {
    return contacts;
  }

  public void setContacts(List<Contact> contacts) {
    this.contacts = contacts;
  }
}
