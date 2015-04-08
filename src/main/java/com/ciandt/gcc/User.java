package com.ciandt.gcc;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;

public class User {
 
    public void setFlagImportedContacts(Key key, boolean valueFlag){
       
        try {
            
            DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
            
            Entity user = datastore.get(key);
            user.setProperty("imported", valueFlag);
            datastore.put(user);
        
        } catch (EntityNotFoundException e) {

            e.printStackTrace();
        }

        
    }
    
    public void setAcessToken(Key key, String newToken, long newTime){

        try {
            
            DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
            
            Entity user = datastore.get(key);
            user.setProperty("accessToken", newToken);
            user.setProperty("expireTime", newTime);
            datastore.put(user);
        
        } catch (EntityNotFoundException e) {

            e.printStackTrace();
        }

        
    }
    
    
    public List<Entity> QueryContactsAncestor(Key ancestorKey){
        
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        
        Query ancestorQuery = new Query("User").setAncestor(ancestorKey); 
        List<Entity> results = datastore.prepare(ancestorQuery).asList(FetchOptions.Builder.withDefaults());
        
        return results;
        
    }
    


}
