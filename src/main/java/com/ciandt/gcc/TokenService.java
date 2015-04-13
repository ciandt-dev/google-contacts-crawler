package com.ciandt.gcc;

import java.util.Random;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class TokenService {

    public static Key GeneratedToken() {

        
            String letras = "ABCDEFGHIJKLMNOPQRSTUVYWXZ";  
            
            Random random = new Random();  
              
            String armazenaChaves = "";  
            int index = 10;  
            for( int i = 0; i < 70; i++ ) {  
               index = random.nextInt( letras.length() );  
               armazenaChaves += letras.substring( index, index + 1 );  
            }
            
            
            Key key = KeyFactory.createKey("Authorization", armazenaChaves);
            String serializedKey = KeyFactory.keyToString(key);
            
            System.out.println(serializedKey); 
            //System.out.println( armazenaChaves );  
            return key;

}
}