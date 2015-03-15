package com.springapp.mvc.ldap;

import com.springapp.mvc.PersonList;
import com.springapp.mvc.model.Person;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;

/**
 * This class is used for creating a more manageable way to access users on the LDAP server.
 * Has two functions for multiple users and single users.
 * @author Adrian Hundseth
 */
@Deprecated
public class SearchProcessing {
    /**
     * Receives a <code>SearchResult</code> object and extracts the fields of the user within.
     * <p>
     *     If the user does not contain certain attributes, the function prints an error.
     * </p>
     * @param searchResult The result of a search on the LDAP server
     * @return Returns a {@link com.springapp.mvc.model.Person} object
     */
    protected static Person getPerson(SearchResult searchResult) {
        Person person = null;
        try {
            Attributes attributes = searchResult.getAttributes();

            Attribute givenName = attributes.get("givenName");
            Attribute sn = attributes.get("sn");
            Attribute uidNumber = attributes.get("uidNumber");
            Attribute telephoneNumber = attributes.get("telephoneNumber");
            Attribute mail = attributes.get("mail");
            Attribute memberOf = attributes.get("memberOf");
            Attribute uid = attributes.get("uid");
            Attribute cn = attributes.get("cn");

            person = new Person();

            if (givenName != null) {
                //System.out.println("First Name: " + firstName.get());
                person.setGivenName("" + givenName.get());
            }
            if (sn != null) {
                //System.out.println("Last Name: " + lastName.get());
                person.setSn("" + sn.get());
            }
            if (cn != null) {
                //System.out.println("Full Name: " + fullName.get());
                person.setCn("" + cn.get());
            }
            if (uid != null) {
                //System.out.println("Username: " + username.get());
                person.setUid("" + uid.get());
            }
            if (mail != null) {
                //System.out.println("Email: " + email.get());
                person.setMail("" + mail.get());
            }
            if (telephoneNumber != null) {
                //System.out.println("Mobile: " + mobile.get());
                person.setTelephoneNumber((String) telephoneNumber.get());
            }
            if (memberOf != null) {
                //System.out.println("Groups:");
                for (int j = 0; j < memberOf.size(); j++) {
                    //System.out.println("\t" + memberOf.get(i));
                    person.memberOf.add("" + memberOf.get(j));
                }
            }
            if (uidNumber != null) {
                //System.out.println("ID: " + id.get());
                person.setUidNumber(Long.valueOf("" + uidNumber.get()));
            }
        }
        catch (NamingException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return person;
    }

    /**
     * Receives a <code>NamingEnumeration</code> object and extracts the fields of all the users within.
     * <p>
     *     If the user does not contain certain attributes, the function prints an error.
     * </p>
     * @param answer A <code>NamingEnumeration</code> that contains the result of an LDAP search
     * @return Returns a {@link com.springapp.mvc.PersonList} of {@link com.springapp.mvc.model.Person} objects
     */
    protected static PersonList getPersons(NamingEnumeration answer) {
        PersonList returnList = new PersonList();
        try {
            //Find the next person with while(answer.hasMore())
            while(answer.hasMore()) {
                Person returnperson = new Person();
                //Get the next result
                SearchResult searchResult = (SearchResult) answer.next();

                //Getting the attributes of the result
                Attributes attributes = searchResult.getAttributes();

                //Storing each attribute
                Attribute firstName = attributes.get("givenName");
                Attribute lastName = attributes.get("sn");
                Attribute id = attributes.get("gidNumber");
                Attribute mobile = attributes.get("telephoneNumber");
                Attribute email = attributes.get("mail");
                Attribute groups = attributes.get("memberOf");
                Attribute fullName = attributes.get("cn");
                Attribute uid = attributes.get("uid");
                Attribute harOblat = attributes.get("harOblat");
                String dn = searchResult.getNameInNamespace();


                if (firstName != null) {
                    //System.out.println("First Name: " + firstName.get());
                    returnperson.setGivenName("" + firstName.get());
                }
                if (lastName != null) {
                    //System.out.println("Last Name: " + lastName.get());
                    returnperson.setSn("" + lastName.get());
                }
                if (fullName != null) {
                    //System.out.println("Full Name: " + fullName.get());
                    returnperson.setCn("" + fullName.get());
                }
                if (uid != null) {
                    //System.out.println("Username: " + username.get());
                    returnperson.setUid("" + uid.get());
                }
                if (email != null) {
                    //System.out.println("Email: " + email.get());
                    returnperson.setMail("" + email.get());
                }
                if (mobile != null) {
                    //System.out.println("Mobile: " + mobile.get());
                    returnperson.setTelephoneNumber((String) mobile.get());
                }
                if (groups != null) {
                    //System.out.println("Groups:");
                    for (int j = 0; j < groups.size(); j++) {
                        //System.out.println("\t" + memberOf.get(i));
                        returnperson.memberOf.add("" + groups.get(j));
                    }
                }
                if (id != null) {
                    //System.out.println("ID: " + id.get());
                    returnperson.setUidNumber(Long.valueOf("" + id.get()));
                }
                if (dn != null) {
                    returnperson.setDn(dn);
                }
                if (harOblat != null) {
                    returnperson.setHarOblat(Boolean.getBoolean("" + harOblat.get()));
                }
                returnList.add(returnperson);
                //System.out.println(returnperson);
            }
        } catch(NamingException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return returnList;
    }
}
