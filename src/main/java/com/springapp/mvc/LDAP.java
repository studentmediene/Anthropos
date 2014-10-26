/*
For this to work, you have to run the program on the same LAN as the LDAP-server
or tunnel a port on your computer to the LDAP-server, for example:
ssh boyeborg@scgw1.studentmediene.no -L8389:ldap.studentmediene.local:389
*/
package com.springapp.mvc;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.ArrayList;
import java.util.Hashtable;


public class LDAP {

    private static final String host = "ldap://ldapstaging.studentmediene.no";
    private static final String name = "ou=Users,dc=studentmediene,dc=no";

    private static Hashtable<String, Object> config() {
		Hashtable<String, Object> env = new Hashtable<String, Object>();

		//Connection details
		env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, host);
		env.put(Context.SECURITY_AUTHENTICATION,"none");

        return env;
    }

    //Recieves a DistinguishedName (i.e. uid=firstname.lastname,ou=Users,dc=studentmediene,dc=no) and a password
    protected static Hashtable<String, Object> config(ActiveLogin activeLogin) throws NamingException {
        Hashtable<String, Object> env = new Hashtable<String, Object>();

        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, host);
        env.put(Context.SECURITY_AUTHENTICATION, "ssl");
        env.put(Context.SECURITY_PRINCIPAL, activeLogin.getUid());
        env.put(Context.SECURITY_CREDENTIALS, activeLogin.getCr());

        return env;
    }

    protected static PersonList search(String search) throws NamingException {
        Hashtable<String, Object> env = config();
        DirContext ctx = new InitialDirContext(env);
        SearchControls ctls = new SearchControls();
        String filter = ("(|(mail=*" + search + "*)(cn=*" + search + "*)(dn=*" + search + "*))");
        NamingEnumeration answer = ctx.search(name, filter, ctls);

        return getPersons(answer);
    }

    /*
     * Used to find the rights level of the current user. Takes the dn and cr and binds to the server.
      * 0 = Write access on your own user, otherwise read-only (Not member of any group in ou=Rights)
      * 1 = Write access within section (Member of cn=seksjonsAdmin,ou=Rights,ou=Groups,dc=studentmediene,dc=studentmediene,dc=no)
      * 2 = Write access level "Gjengsjef", can edti everyone except it-drift (Member of cn=gjengadmin,ou=Rights,ou=Groups,dc=studentmediene,dc=studentmediene,dc=no)
      * 3 = IT-Drift (Member of cn=superAdmin,ou=Rights,ou=Groups,dc=studentmediene,dc=studentmediene,dc=no)
      * */
    protected static int checkRightsLevel(String dn, String cr) throws NamingException {
        Hashtable<String, Object> env = config(new ActiveLogin(dn, cr));
        int rightsLevel = 0;
        try {
            InitialDirContext ctx = new InitialDirContext(env);
            SearchControls ctls = new SearchControls();
            String filter = ("(" + dn + ")");
            NamingEnumeration answer = ctx.search(name, filter, ctls);

            SearchResult searchResult = (SearchResult) answer.next();
            Person person = getPerson(searchResult);
            ArrayList<String> sections = new ArrayList<String>();
            for (String group : person.getGroups()) {
                if (group.contains("section")) {
                    sections.add(group);
                }
            }
            for (String section : sections) {
                if (section.contains("superAdmin")) {
                    rightsLevel = 3;
                } else if (section.contains("gjengAdmin")) {
                    rightsLevel = (rightsLevel < 2) ? 2 : rightsLevel;
                } else if (section.contains("seksjonsAdmin")) {
                    rightsLevel = (rightsLevel < 1) ? 1 : rightsLevel;
                }
            }
            ctx.close();
        } catch (AuthenticationException e) {
            System.err.println("Authentication error: " + e.getMessage());
        }
        return rightsLevel;
    }

    protected static int checkRightsLevelSimple(String uid) throws NamingException {
        Hashtable<String, Object> env = config();
        int rightsLevel = 0;
        try {
            InitialDirContext ctx = new InitialDirContext(env);
            SearchControls ctls = new SearchControls();
            String filter = "uid=" + uid;
            NamingEnumeration answer = ctx.search(name, filter, ctls);

            SearchResult user = (SearchResult) answer.next();
            Attributes attributes = user.getAttributes();
            Attribute groups = attributes.get("groups");
            ArrayList<String> sections = new ArrayList<String>();
            if (groups != null) {
                for (int j = 0; j < groups.size(); j++) {
                    //System.out.println("\t" + groups.get(i));
                    sections.add("" + groups.get(j));
                }

                for (String section : sections) {
                    if (section.contains("superAdmin")) {
                        rightsLevel = 3;
                    } else if (section.contains("gjengAdmin")) {
                        rightsLevel = (rightsLevel < 2) ? 2 : rightsLevel;
                    } else if (section.contains("seksjonsAdmin")) {
                        rightsLevel = (rightsLevel < 1) ? 1 : rightsLevel;
                    }
                }
            }
        } catch (AuthenticationException e) {
            System.err.println("Authentication error: " + e.getMessage());
        }
        //System.out.println(rightsLevel);
        return rightsLevel;
    }

    protected static String getDn(String uid) throws NamingException {
        Hashtable<String, Object> env = config();
        DirContext ctx = new InitialDirContext(env);
        SearchControls ctls = new SearchControls();
        String filter = ("uid=" + uid);
        NamingEnumeration answer = ctx.search(name, filter, ctls);

        SearchResult searchResult = (SearchResult) answer.next();
        if (answer.hasMoreElements()) {
            System.err.println("Matched mutliple users for the uid" + uid);
            return null;
        }
        ctx.close();
        return searchResult.getNameInNamespace();
    }

    protected static Person findByIdNumber(int id) throws NamingException {
        Hashtable<String, Object> env = config();
        DirContext ctx = new InitialDirContext(env);
        SearchControls ctls = new SearchControls();
        String filter = ("(|(uidNumber=" + id + "))");
        NamingEnumeration answer = ctx.search(name, filter, ctls);

        SearchResult searchResult = (SearchResult) answer.next();
        if(answer.hasMoreElements()) {
            System.err.println("Matched multiple users for the uidNumber: " + id);
            return null;
        }
        ctx.close();
        return getPerson(searchResult);
    }

    protected static void edit(ActiveLogin activeLogin, String field, String value) throws NamingException {
        Hashtable<String, Object> env = config(activeLogin);

        DirContext ctx = new InitialDirContext(env);
        ModificationItem[] mods = new ModificationItem[1];
        Attribute mod = new BasicAttribute(field, value);
        mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod);
        ctx.modifyAttributes(getDn(activeLogin.getUid()), mods);
    }

	protected static PersonList retrieve() throws NamingException {
        Hashtable<String, Object> env = config();

		DirContext ctx = new InitialDirContext(env);
		
		//Search controller
        SearchControls ctls = new SearchControls();
        //String[] attrIDs = {"givenName", "sn", "gidNumber", "telephoneNumber", "mail", "memberOf", "dn", "cn"};
        //ctls.setReturningAttributes(attrIDs);

		//The actual search
		NamingEnumeration answer = ctx.search("ou=Users,dc=studentmediene,dc=no", "(&(memberOf=*))", ctls);

        ctx.close();
        return getPersons(answer);
	}

    private static Person getPerson(SearchResult searchResult) {
        Person person = null;
        try {
            Attributes attributes = searchResult.getAttributes();

            Attribute firstName = attributes.get("givenName");
            Attribute lastName = attributes.get("sn");
            Attribute id = attributes.get("uidNumber");
            Attribute mobile = attributes.get("telephoneNumber");
            Attribute email = attributes.get("mail");
            Attribute groups = attributes.get("memberOf");
            Attribute username = attributes.get("uid");
            Attribute fullName = attributes.get("cn");

            person = new Person();

            if (firstName != null) {
                //System.out.println("First Name: " + firstName.get());
                person.setFirstName("" + firstName.get());
            }
            if (lastName != null) {
                //System.out.println("Last Name: " + lastName.get());
                person.setLastName("" + lastName.get());
            }
            if (fullName != null) {
                //System.out.println("Full Name: " + fullName.get());
                person.setFullName("" + fullName.get());
            }
            if (username != null) {
                //System.out.println("Username: " + username.get());
                person.setUid("" + username.get());
            }
            if (email != null) {
                //System.out.println("Email: " + email.get());
                person.setEmail("" + email.get());
            }
            if (mobile != null) {
                //System.out.println("Mobile: " + mobile.get());
                person.setMobile(Integer.valueOf("" + mobile.get()));
            }
            if (groups != null) {
                //System.out.println("Groups:");
                for (int j = 0; j < groups.size(); j++) {
                    //System.out.println("\t" + groups.get(i));
                    person.groups.add("" + groups.get(j));
                }
            }
            if (id != null) {
                //System.out.println("ID: " + id.get());
                person.setId(Integer.valueOf("" + id.get()));
            }
        }
            catch (NamingException e) {
                System.err.println("Error: " + e.getMessage());
            }
        return person;
    }

private static PersonList getPersons(NamingEnumeration answer) {
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
            String dn = searchResult.getNameInNamespace();


            if (firstName != null) {
                //System.out.println("First Name: " + firstName.get());
                returnperson.setFirstName("" + firstName.get());
            }
            if (lastName != null) {
                //System.out.println("Last Name: " + lastName.get());
                returnperson.setLastName("" + lastName.get());
            }
            if (fullName != null) {
                //System.out.println("Full Name: " + fullName.get());
                returnperson.setFullName("" + fullName.get());
            }
            if (uid != null) {
                //System.out.println("Username: " + username.get());
                returnperson.setUid("" + uid.get());
            }
            if (email != null) {
                //System.out.println("Email: " + email.get());
                returnperson.setEmail("" + email.get());
            }
            if (mobile != null) {
                //System.out.println("Mobile: " + mobile.get());
                returnperson.setMobile(Integer.valueOf("" + mobile.get()));
            }
            if (groups != null) {
                //System.out.println("Groups:");
                for (int j = 0; j < groups.size(); j++) {
                    //System.out.println("\t" + groups.get(i));
                    returnperson.groups.add("" + groups.get(j));
                }
            }
            if (id != null) {
                //System.out.println("ID: " + id.get());
                returnperson.setId(Integer.valueOf("" + id.get()));
            }
            if (dn != null) {
                returnperson.setDn(dn);
            }
            returnperson.setRightsLevel(checkRightsLevelSimple((String) uid.get()));
            returnList.add(returnperson);
            //System.out.println(returnperson);
        }
    } catch(NamingException e) {
        System.err.println("Error: " + e.getMessage());
    }
    return returnList;
}
	
}