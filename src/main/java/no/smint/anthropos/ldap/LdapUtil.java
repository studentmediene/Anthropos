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

package no.smint.anthropos.ldap;

import no.smint.anthropos.PersonList;
import no.smint.anthropos.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.stereotype.Service;

import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

/**
 * Created by adrianh on 01.03.15.
 */
@Service
@Configurable
public class LdapUtil {

    LdapTemplate tmpl;
    private static final String host = "ldap://ldapstaging.studentmediene.no:389";
    private static final String name = "dc=studentmediene,dc=no";

    /**
     * TODO: This doesn't work yet for some reason. In the meantime, use the manual creation of tmpl, a temporary ldapTemplate.
     */
    @Autowired
    LdapTemplate ldapTemplate;

    public LdapUtil() {
        LdapContextSource ctxSrc = new LdapContextSource();
        ctxSrc.setUrl(host);
        ctxSrc.setBase(name);
        ctxSrc.setUserDn("");
        ctxSrc.setPassword("");

        ctxSrc.afterPropertiesSet();

        this.tmpl = new LdapTemplate(ctxSrc);
    }

    Logger logger = LoggerFactory.getLogger(getClass());

    public PersonList getUsers() {
        final int[] activeCount = {0};
        PersonList personList = new PersonList();
        logger.info("ldapTemplate context source: " + tmpl.getContextSource());
            List<Person> persons = tmpl.search("ou=Users", "(objectClass=person)", new PersonAttributesMapper());

        logger.info("Number of users from LDAP: {}", persons.size());
        logger.info("Number of active: {}", activeCount[0]);

        personList.update(persons);
        return personList;
    }

    public Person getUser(String dn) {
        return tmpl.lookup(dn, new PersonAttributesMapper());
    }

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

