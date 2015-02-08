package com.springapp.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by adrianh on 25.01.15.
 */
@Service
public class TokenAuthenticationProvider implements AuthenticationProvider {
    Logger logger = LoggerFactory.getLogger(getClass());

    private PersonList personList;

    @Autowired
    LdapTemplate ldapTemplate;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Token token = (Token) authentication;
        LdapUserPwd ldapUserPwd = token.getLdapUserPwd();

        if (validateLogin(ldapUserPwd)) {
            Person loggedInUser = getLoggedInUser(ldapUserPwd.getUsername());
            AuthUserDetails authUserDetails = new AuthUserDetails(loggedInUser);

            // Return an updated token with the right user details
            return new Token(ldapUserPwd, authUserDetails);
        }
        throw new BadCredentialsException("Invalid username or password");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(Token.class);
    }

    private boolean validateLogin(LdapUserPwd ldapUserPwd) {

        boolean authenticate = ldapTemplate.authenticate("ou=Users", "(uid=" + ldapUserPwd.getUsername() + ")" , ldapUserPwd.getPassword());

        if (authenticate) {
            logger.info("User with username {} was authenticated by LDAP", ldapUserPwd.getUsername());
        } else {
            logger.error("Wrong username or password for user {}", ldapUserPwd.getUsername());
        }

        return authenticate;
    }

    private Person getLoggedInUser(String username) {
        Person person = personList.getPersonByUid(username);
        if (person == null) {
            throw new RestException("User was logged in, but not found in our database!", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return person;
    }
}