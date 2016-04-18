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
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class LdapUtil {

    @Autowired
    private LdapTemplate ldapTemplate;

    Logger logger = LoggerFactory.getLogger(getClass());

    public PersonList getUsers() {
        final int[] activeCount = {0};
        PersonList personList = new PersonList();
        logger.info("ldapTemplate context source: " + ldapTemplate);
        List<Person> persons = ldapTemplate.search("ou=Users", "(objectClass=person)", new PersonAttributesMapper());

        logger.info("Number of users from LDAP: {}", persons.size());
        logger.info("Number of active: {}", activeCount[0]);

        personList.update(persons);

        for (Person p : personList) {
            ArrayList<String> groups = p.getMemberOf();
            ArrayList<String> sections = new ArrayList<String>();
            for (String group : groups) {
                if (group.contains("sections")) {
                    sections.add(group.substring(group.indexOf('=') + 1, group.indexOf(',')));
                }
            }
            p.setMemberOf(sections);
        }
        return personList;
    }

    public Person getUserById(int id) {
        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("uidNumber", id));
        return ldapTemplate.search("ou=Users", filter.encode(), new PersonAttributesMapper()).get(0);
    }

    public Person addUser(Person person) {
        return null;
    }
}

