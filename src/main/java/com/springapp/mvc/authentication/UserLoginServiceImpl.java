package com.springapp.mvc.authentication;

import com.springapp.mvc.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Created by adrianh on 08.02.15.
 */
@Service
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Long getId() {
        return getLoggedInUserDetails().getUidNumber();
    }

    @Override
    public String getUsername() {
        return getLoggedInUserDetails().getUsername();
    }

    @Override
    public boolean login(LdapUserPwd token) {
        Authentication authentication = authenticationManager.authenticate(new Token(token));
        boolean isAuthenticated = isAuthenticated(authentication);
        if (isAuthenticated) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        return isAuthenticated;
    }

    @Override
    public void logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @Override
    public Person getLoggedInUser() {
        return null;
    }

    public AuthUserDetails getLoggedInUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (isAuthenticated(authentication)) {
            return (AuthUserDetails) authentication.getPrincipal();
        }
        return null;
    }

    private boolean isAuthenticated(Authentication authentication) {
        return authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
}
