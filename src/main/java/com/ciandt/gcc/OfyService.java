package com.ciandt.gcc;

import com.ciandt.gcc.entities.*;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

public class OfyService {
    static {
        ObjectifyService.register(Contact.class);
        ObjectifyService.register(Credential.class);
        ObjectifyService.register(User.class);
    }
    
    public static Objectify ofy() {
        return ObjectifyService.ofy(); 
    }
    
    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}
