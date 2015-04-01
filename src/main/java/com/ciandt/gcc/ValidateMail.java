package com.ciandt.gcc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateMail {

    private Pattern pattern;
    private Matcher matcher;
 
    private String expression = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@ciandt.com";
 

    public boolean validate(String mail) {

        pattern = Pattern.compile(expression);
        matcher = pattern.matcher(mail);
        return matcher.matches();
           
    }
    
}