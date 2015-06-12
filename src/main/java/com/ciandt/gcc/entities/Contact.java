package com.ciandt.gcc.entities;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

@Cache
@Entity
public class Contact {
    @Id Long id;
    
    @Parent Ref<User> owner;
    
    String name;
    
    String email;
    
    @SuppressWarnings("unused")
    private Contact() {}
    
    public Contact(String email) {
        this.email = email;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getEmail() {
      return email;
    }
}
