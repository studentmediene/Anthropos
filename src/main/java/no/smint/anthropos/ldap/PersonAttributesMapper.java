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

import no.smint.anthropos.model.Person;
import org.springframework.ldap.core.AttributesMapper;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import java.util.ArrayList;

/**
 * Created by adrianh on 01.03.15.
 */
public class PersonAttributesMapper implements AttributesMapper<Person> {

        public Person mapFromAttributes(Attributes attributes) throws NamingException {
            Person person = new Person();
            person.setUidNumber(Long.valueOf((String) attributes.get("uidNumber").get()));
            person.setGivenName(attributes.get("givenName") != null ? (String) attributes.get("givenName").get() : "");
            person.setSn(attributes.get("sn") != null ? (String) attributes.get("sn").get() : "");
            person.setCn(attributes.get("cn") != null ? (String) attributes.get("cn").get() : "");
            person.setUid(attributes.get("uid") != null ? (String) attributes.get("uid").get() : "");
            person.setMail(attributes.get("mail") != null ? (String) attributes.get("mail").get() : "");
            person.setTelephoneNumber(attributes.get("telephoneNumber") != null ? (String) attributes.get("telephoneNumber").get() : "");

            Attribute memberOfAtt = attributes.get("memberOf");
            if (memberOfAtt != null) {
                ArrayList<String> memberOf = new ArrayList<String>();
                for (int i = 0; i < memberOfAtt.size(); i++) {
                    memberOf.add((String) memberOfAtt.get(i));
                }
                person.setMemberOf(memberOf);
            }
            return person;
        }
}