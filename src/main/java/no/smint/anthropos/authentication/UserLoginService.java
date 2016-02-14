package no.smint.anthropos.authentication;

import no.smint.anthropos.model.Person;

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
