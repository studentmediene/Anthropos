package com.springapp.mvc.authentication;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Created by adrianh on 25.01.15.
 */
public class LdapUserPwd {

    private String username;
    private String password;

    @JsonIgnore
    public LdapUserPwd() {

    }

    public LdapUserPwd(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "LdapUserPwd{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
