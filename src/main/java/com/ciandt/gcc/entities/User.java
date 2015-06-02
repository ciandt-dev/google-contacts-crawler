package com.ciandt.gcc;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;

public class User {

    public void setFlagImportedContacts(Key key, boolean valueFlag) {

        try {

            DatastoreService datastore = DatastoreServiceFactory
                    .getDatastoreService();

            Entity user = datastore.get(key);
            user.setProperty("imported", valueFlag);
            datastore.put(user);

        } catch (EntityNotFoundException e) {

            e.printStackTrace();
        }

    }

    public void setAcessToken(Key key, String newToken, long newTime) {

        try {

            DatastoreService datastore = DatastoreServiceFactory
                    .getDatastoreService();

            Entity user = datastore.get(key);
            user.setProperty("accessToken", newToken);
            user.setProperty("expireTime", newTime);
            datastore.put(user);

        } catch (EntityNotFoundException e) {

            e.printStackTrace();
        }

    }

    public JSONArray QueryContactsAncestor(String userEmail) throws JSONException {

        DatastoreService datastore = DatastoreServiceFactory
                .getDatastoreService();

        Key requestUser = KeyFactory.createKey("User", userEmail);

        Query ancestorQuery = new Query("Contact").setAncestor(requestUser);
        List<Entity> results = datastore.prepare(ancestorQuery).asList(
                FetchOptions.Builder.withDefaults());

         JSONArray jsonResponseArray = new JSONArray();
        
        
        for(Entity contact : results){
            
            JSONObject contactJson = new JSONObject();
            contactJson.put("name", contact.getProperty("name"));
            contactJson.put("mail", contact.getKey().getName());

            jsonResponseArray.put(contactJson);
        }
        
        return jsonResponseArray;
    }

    public JSONArray QueryContacts() throws JSONException {

        DatastoreService datastore = DatastoreServiceFactory
                .getDatastoreService();

        Query ancestorQueryUser = new Query("User");
        List<Entity> results = datastore.prepare(ancestorQueryUser).asList(
                FetchOptions.Builder.withDefaults());
        
        JSONArray jsonResponseArray = new JSONArray();
        
        
        for(Entity user : results){
            
            JSONObject userJson = new JSONObject();
            userJson.put("mail", user.getKey().getName());
            
            Query ancestorQueryContacts = new Query("Contact").setAncestor(user.getKey());
            List<Entity> resultsContacts = datastore.prepare(ancestorQueryContacts).asList(
                    FetchOptions.Builder.withDefaults());
            
            
            List<JSONObject> list = new ArrayList<JSONObject>();
            
            for(Entity contact : resultsContacts){
                
                JSONObject contactJson = new JSONObject();
                
                contactJson.put("name", contact.getProperty("name"));
                contactJson.put("mail", contact.getKey().getName());
                
                list.add(contactJson);
              
            }
            
            
            userJson.put("Contacts", list);
            
            jsonResponseArray.put(userJson);
            
            
        }

        return jsonResponseArray;

    }

}
