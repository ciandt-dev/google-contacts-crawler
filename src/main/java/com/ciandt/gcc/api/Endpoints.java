package com.ciandt.gcc.api;

import java.util.ArrayList;
import java.util.List;

import com.ciandt.gcc.OfyService;
import com.ciandt.gcc.entities.User;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.response.NotFoundException;
import com.googlecode.objectify.Objectify;

@Api(
    name = "gcc",
    version = "v1",
    description = "Google Contacts Crawler API",
    clientIds = {"1049893588094-1dckqbdi19hs0k3qj8vluqt17i90uai3.apps.googleusercontent.com"}
)
public class Endpoints {
 
    @ApiMethod(path="contacts")
    public List<UserBean> getContacts() throws NotFoundException {
      Objectify ofy = OfyService.ofy();
      List<User> users = ofy.load().type(User.class).filter("imported", true).list();
      List<UserBean> response = new ArrayList<>();
      
      for (User user : users) {
          UserBean userBean = new UserBean();
          userBean.setEmail(user.getEmail());
          userBean.setContacts(user.getContacts());
          response.add(userBean);
      }
      
      return response;
    }
}

