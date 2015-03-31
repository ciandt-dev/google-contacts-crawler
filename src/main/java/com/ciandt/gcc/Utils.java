package com.ciandt.gcc;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.http.*;
import com.google.api.client.http.javanet.*;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.*;

public class Utils {
  
  private static final String CLIENTSECRETS_LOCATION = "/client_secrets.json";
  private static final String REDIRECT_URI = "/oauth2callback";
  private static final List<String> SCOPES = Arrays.asList(
      "https://www.googleapis.com/auth/contacts.readonly");
  
  static String getRedirectUri(HttpServletRequest req) {
    GenericUrl url = new GenericUrl(req.getRequestURL().toString());
    url.setRawPath(REDIRECT_URI);
    return url.build();
  }

  static GoogleAuthorizationCodeFlow newFlow() throws IOException {
    HttpTransport httpTransport = new NetHttpTransport();
    JsonFactory jsonFactory = new JacksonFactory();

    Reader reader = new InputStreamReader(Utils.class.getResourceAsStream(CLIENTSECRETS_LOCATION));
    GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(new JacksonFactory(), reader);
    return
        new GoogleAuthorizationCodeFlow.Builder(httpTransport, jsonFactory, clientSecrets, SCOPES)
            .setAccessType("offline").setApprovalPrompt("force").build();
  }
}
