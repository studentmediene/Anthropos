package com.springapp.mvc.authentication;

import com.springapp.mvc.model.Person;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * A lightweight object holding info about the currently logged in user
 */
public class AuthUserDetails implements UserDetails {

    private Long uidNumber;
    private final String uid;

    public AuthUserDetails(Person person) {
        uidNumber = person.getUidNumber();
        uid = person.getUid();
    }

    public Long getUidNumber() {
        return uidNumber;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return uid;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
