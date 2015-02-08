package com.springapp.mvc.authentication;

import com.springapp.mvc.model.Person;

/**
 * Created by adrianh on 08.02.15.
 */
public interface UserLoginService {
    Long getId();

    String getUsername();

    boolean login(LdapUserPwd token);

    void logout();

    Person getLoggedInUser();
}
