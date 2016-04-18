/*
 * Copyright 2016 Studentmediene i Trondheim AS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package no.smint.anthropos.authentication;

import no.smint.anthropos.PersonList;
import no.smint.anthropos.RestException;
import no.smint.anthropos.ldap.LdapUtil;
import no.smint.anthropos.ldap.PersonAttributesMapper;
import no.smint.anthropos.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Service
public class TokenAuthenticationProvider implements AuthenticationProvider {
    Logger logger = LoggerFactory.getLogger(getClass());

    private PersonList personList = new PersonList();
    private LdapUtil ldapUtil = new LdapUtil();

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

        logger.info("LdapTemplate context source: " + ldapTemplate.getContextSource(), ldapTemplate.getContextSource());
        boolean authenticate = ldapTemplate.authenticate("ou=Users", "(uid=" + ldapUserPwd.getUsername() + ")" , ldapUserPwd.getPassword());

        if (authenticate) {
            logger.info("User with username {} was authenticated by LDAP", ldapUserPwd.getUsername());
        } else {
            logger.error("Wrong username or password for user {}", ldapUserPwd.getUsername());
        }

        return authenticate;
    }

    private Person getLoggedInUser(String username) {
        System.out.println(username);
        Person person = new Person();
        try {
            person = search(username).get(0);
        } catch (NamingException e) {
            e.printStackTrace();
        }
        if (person == null) {
            throw new RestException("User was logged in, but not found in our database!", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return person;
    }


    //TODO: This way is less than ideal. Search can not be referenced from LdapUtil because a new instance breaks the autowiring of ldapTemplate. Try to find a solution to this.
    public PersonList search(String search) throws NamingException {
        PersonList personList = new PersonList();
        SearchControls ctls = new SearchControls();
        LdapQuery query = query()
                .where("objectclass").is("person")
                .and("uid").is(search);
        List<Person> persons = ldapTemplate.search(query, new PersonAttributesMapper());

        personList.update(persons);
        return personList;
    }
}
