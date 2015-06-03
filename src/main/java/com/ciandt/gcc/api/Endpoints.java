package com.ciandt.gcc.api;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.ciandt.gcc.PMF;
import com.ciandt.gcc.entities.Contact;
import com.ciandt.gcc.entities.User;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.response.NotFoundException;

@Api(
    name = "gcc",
    version = "v1",
    description = "Google Contacts Crawler API",
    clientIds = {"1049893588094-1dckqbdi19hs0k3qj8vluqt17i90uai3.apps.googleusercontent.com"}
)
public class Endpoints {
 
    @ApiMethod(path="contacts")
    public List<UserBean> getContacts() throws NotFoundException {
      PersistenceManager pm = PMF.get().getPersistenceManager();
      
      Query query = pm.newQuery(User.class);
      query.setFilter("imported == true");
      List<User> results = (List<User>) query.execute();
      List<UserBean> response = new ArrayList<>();
      
      for (User user : results) {
          UserBean userBean = new UserBean();
          userBean.setEmail(user.getEmail());
          userBean.setContacts(user.getContacts());
          response.add(userBean);
      }
      
      return response;
    }
}

