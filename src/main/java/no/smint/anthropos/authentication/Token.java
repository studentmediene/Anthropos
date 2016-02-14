package no.smint.anthropos.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by adrianh on 25.01.15.
 */
public class Token extends AbstractAuthenticationToken {

    private LdapUserPwd ldapUserPwd;
    private UserDetails principal;

    /**
     * Standard, non-authenticated token
     */
    public Token(LdapUserPwd ldapUserPwd) {
        super(null);
        this.ldapUserPwd = ldapUserPwd;
        setAuthenticated(false);
    }

    /**
     * When the user has been verified
     */
    public Token(LdapUserPwd ldapUserPwd, AuthUserDetails principal) {
        super(principal.getAuthorities());
        this.ldapUserPwd = ldapUserPwd;
        this.principal = principal;
        setAuthenticated(true);
    }

    public LdapUserPwd getLdapUserPwd() {
        return ldapUserPwd;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return  principal;
    }
}
