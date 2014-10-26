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

    //private static final String host = "ldap://localhost:8389"; //For testing on the real server. Must tunnel to scgwl.studentmediene.no
    /*
        For this to work, you have to run the program on the same LAN as the LDAP-server
        or tunnel a port on your computer to the LDAP-server, for example:
        ssh boyeborg@scgw1.studentmediene.no -L8389:ldap.studentmediene.local:389
    */

    private static final String host = "ldap://ldapstaging.studentmediene.no";
    private static final String name = "ou=Users,dc=studentmediene,dc=no";

    private static Hashtable<String, Object> config() {
		Hashtable<String, Object> env = new Hashtable<String, Object>();

		//Connection details
		env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, host);
		env.put(Context.SECURITY_AUTHENTICATION,"none");

        //Optional for testing purposes
        //env.put(Context.SECURITY_AUTHENTICATION, "simple");
        //env.put(Context.SECURITY_PRINCIPAL, "uid=USERNAME HERE,ou=System Users,dc=studentmeidene,dc=no");
        //env.put(Context.SECURITY_CREDENTIALS, "PASSWORD");

        return env;
    }

    //Recieves a DistinguishedName in the form of an Active Login object (i.e. uid=firstname.lastname,ou=Users,dc=studentmediene,dc=no) and a password
    protected static Hashtable<String, Object> config(ActiveLogin activeLogin) throws NamingException {
        Hashtable<String, Object> env = new Hashtable<String, Object>();

        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, host);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
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

        return SearchProcessing.getPersons(answer);
    }

    /*
     * Used to find the rights level of the current user. Takes the dn and cr and binds to the server.
      * 0 = Write access on your own user, otherwise read-only (Not member of any group in ou=Rights)
      * 1 = Write access within section (Member of cn=seksjonsAdmin,ou=Rights,ou=Groups,dc=studentmediene,dc=studentmediene,dc=no)
      * 2 = Write access level "Gjengsjef", can edit everyone except it-drift (Member of cn=gjengadmin,ou=Rights,ou=Groups,dc=studentmediene,dc=studentmediene,dc=no)
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
            Person person = SearchProcessing.getPerson(searchResult);
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
        return SearchProcessing.getPerson(searchResult);
    }

    protected static void edit(ActiveLogin activeLogin, ArrayList<String[]> fields) throws NamingException {
        Hashtable<String, Object> env = config(activeLogin);

        DirContext ctx = new InitialDirContext(env);

        ModificationItem[] mods = new ModificationItem[fields.size()];
        for (String[] field : fields) {
            Attribute mod = new BasicAttribute(field[0], field[1]);
            mods[fields.indexOf(field)] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod);
        }
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
        return SearchProcessing.getPersons(answer);
	}

}