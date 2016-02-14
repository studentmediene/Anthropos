package no.smint.anthropos.authentication;

/**
 * Created by adrianh on 25.01.15.
 */
public class LdapUserPwd {

    private String username;
    private String password;

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
                ", password='" + "[SECRET]" + '\'' +
                '}';
    }
}
