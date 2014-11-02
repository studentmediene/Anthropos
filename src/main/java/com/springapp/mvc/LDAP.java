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


/**
 * This class is a collection of static functions used to interact with the LDAP server.
 * @author Adrian Hundseth
 */
public class LDAP {

    //private static final String host = "ldap://localhost:8389"; //For testing on the real server. Must tunnel to scgwl.studentmediene.no
    /*
        For this to work, you have to run the program on the same LAN as the LDAP-server
        or tunnel a port on your computer to the LDAP-server, for example:
        ssh boyeborg@scgw1.studentmediene.no -L8389:ldap.studentmediene.local:389
    */

    private static final String host = "ldap://ldapstaging.studentmediene.no";
    private static final String name = "ou=Users,dc=studentmediene,dc=no";

    public static void main(String[] args) {
        ActiveLogin activeLogin = null;
        try {
            activeLogin = new ActiveLogin(getDn("birgith.do"), "overrated rapid machine");
            Hashtable<String, Object> env = config(activeLogin);
            System.out.println(env.values());


            //Old phone number: 48181928
            ArrayList<String[]> editList = new ArrayList<String[]>();
            String[] list = {"telephoneNumber", "97292149"};
            editList.add(0, list);
            edit(activeLogin, activeLogin.getDn(), editList);

        } catch(NamingException e) {
            System.err.println("NamingException: " + e.getMessage());
        }
    }

    /**
     * Binds anonymously to the LDAP server. Returns a <code>Hashtable</code> to use for searching etc.
     * @return <code>Hashtable</code> with the binding to the server.
     */
    private static Hashtable<String, Object> config() {
		Hashtable<String, Object> env = new Hashtable<String, Object>();

		//Connection details
		env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, host);
		env.put(Context.SECURITY_AUTHENTICATION,"none");

        //Optional for testing purposes
        //env.put(Context.SECURITY_AUTHENTICATION, "simple");
        //env.put(Context.SECURITY_PRINCIPAL, "uid=birgith.do,ou=System Users,dc=studentmeidene,dc=no");
        //env.put(Context.SECURITY_CREDENTIALS, "overrated rapid machine");
        return env;
    }

    /**
     * Receives a <code>DistinguishedName</code> in the form of an <code>ActiveLogin</code> object (i.e. uid=firstname.lastname,ou=Users,dc=studentmediene,dc=no) and a password.
     * It then binds the provided username to the server. It returns the bind in the form of a <code>Hashtable</code>.
     * Throws a <code>NamingException</code> if the username is not found on the server.
     * @param activeLogin An object containing the <code>DistinguishedName</code> and the credentials
     * @return Returns a <code>Hashtable</code> of the binding to the server.
     * @throws NamingException Thrown if the <code>DistinguishedName</code> is not found on the server
     */
    protected static Hashtable<String, Object> config(ActiveLogin activeLogin) throws NamingException {
        Hashtable<String, Object> env = new Hashtable<String, Object>();

        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, host);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, activeLogin.getDn());
        env.put(Context.SECURITY_CREDENTIALS, activeLogin.getCr());

        return env;
    }

    /**
     * Searches the server for the supplied <code>String</code>. Searches mail, name and <code>DistinguishedName</code>.
     * <p>
     *     Uses the {@link com.springapp.mvc.SearchProcessing#getPersons(javax.naming.NamingEnumeration)} function in {@link com.springapp.mvc.SearchProcessing}
     * </p>
     * <p>
     *     Returns a <code>PersonList</code> object with all the users matching the search terms.
     *     Throws a <code>NamingException</code> received from the {@link com.springapp.mvc.LDAP#config(ActiveLogin)} function.
     * </p>
     * @param search The <code>String</code> to search for.
     * @return Returns a <code>PersonList</code> object with <code>Person</code> objects corresponding to the search term
     * @throws NamingException Thrown upwards by the <code>config</code> function
     * @see com.springapp.mvc.SearchProcessing
     * @see com.springapp.mvc.PersonList
     */
    protected static PersonList search(String search) throws NamingException {
        Hashtable<String, Object> env = config();
        DirContext ctx = new InitialDirContext(env);
        SearchControls ctls = new SearchControls();
        String filter = ("(|(mail=*" + search + "*)(cn=*" + search + "*)(dn=*" + search + "*))");
        NamingEnumeration answer = ctx.search(name, filter, ctls);

        return SearchProcessing.getPersons(answer);
    }

    /**
     * Used to find the rights level of the current user. Takes the dn and cr and binds to the server.
     * <p>
     *     0 = Write access on your own user, otherwise read-only (Not member of any group in ou=Rights)
     * </p>
     * <p>
     *     1 = Write access within right (Member of cn=seksjonsAdmin,ou=Rights,ou=Groups,dc=studentmediene,dc=studentmediene,dc=no)
     * </p>
     * <p>
     *    2 = Write access level "Gjengsjef", can edit everyone except it-drift (Member of cn=gjengadmin,ou=Rights,ou=Groups,dc=studentmediene,dc=studentmediene,dc=no)
     * </p>
     * <p>
     *    3 = IT-Drift (Member of cn=superAdmin,ou=Rights,ou=Groups,dc=studentmediene,dc=studentmediene,dc=no)
     * </p>
     * @param activeLogin An <code>ActiveLogin</code> object with a <code>DistinguishedName</code> and the credentials
     * @param editDn The <code>DistinguishedName</code> of the user that is being edited
     * @return Returns the rights level between the two supplied users
     */
     protected static int checkRightsLevel(ActiveLogin activeLogin, String editDn) {
        try {
            Hashtable<String, Object> env = config(activeLogin);
            InitialDirContext ctx = new InitialDirContext(env);
            SearchControls ctls = new SearchControls();

            String filter = ("(" + activeLogin.getDn() + ")");
            NamingEnumeration answer = ctx.search(name, filter, ctls);
            SearchResult searchResult = (SearchResult) answer.next();
            Person person = SearchProcessing.getPerson(searchResult);
            ArrayList<String> sections = new ArrayList<String>();
            ArrayList<String> rights = new ArrayList<String>();
            for (String group : person.getGroups()) {
                if (group.contains("section")) {
                    sections.add(group);
                } else if (group.contains("Rights")) {
                    rights.add(group);
                }
            }

            filter = ("(" + editDn + ")");
            answer = ctx.search(name, filter, ctls);
            searchResult = (SearchResult) answer.next();
            Person editPerson = SearchProcessing.getPerson(searchResult);
            ArrayList<String> editSections = new ArrayList<String>();
            ArrayList<String> editRights = new ArrayList<String>();
            for (String group : editPerson.getGroups()) {
                if (group.contains("section")) {
                    editSections.add(group);
                } else if (group.contains("Rights")) {
                    editRights.add(group);
                }
            }

            for (String right : rights) {
                if (right.contains("superAdmin")) {
                    return 3;
                } else if(right.contains("gjengAdmin")) {
                    for (String editRight : editRights) {
                        if (editRight.contains("superAdmin")) {
                            return 0;
                        } else {
                            return 2;
                        }
                    }
                } else if(right.contains("seksjonsAdmin")) {
                    for (String section : sections) {
                        for (String editSection : editSections) {
                            if (section.equals(editSection)) {
                                return 1;
                            }
                        }
                    }
                }
            }
            ctx.close();
        } catch (AuthenticationException e) {
            System.err.println("Authentication error: " + e.getMessage());
        } catch (NamingException e) {
            System.err.println("NamingException: " + e.getMessage());
        }
        return 0;
    }

    protected static String getDn(String uid) throws NamingException {
        Hashtable<String, Object> env = config();
        DirContext ctx = new InitialDirContext(env);
        SearchControls ctls = new SearchControls();
        String filter = ("uid=" + uid);
        NamingEnumeration answer = ctx.search(name, filter, ctls);

        SearchResult searchResult;

        if (answer.hasMoreElements()) {
            searchResult = (SearchResult) answer.next();
        } else {
            System.out.println("Found no user by that name");
            return null;
        }
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

    protected static void edit(ActiveLogin activeLogin, String editDn, ArrayList<String[]> fields) throws NamingException {
        if (canEdit(activeLogin, editDn)) {

        }
        Hashtable<String, Object> env = config(activeLogin);

        DirContext ctx = new InitialDirContext(env);

        ModificationItem[] mods = new ModificationItem[fields.size()];
        for (String[] field : fields) {
            Attribute mod = new BasicAttribute(field[0], field[1]);
            mods[fields.indexOf(field)] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod);
        }
        ctx.modifyAttributes(activeLogin.getDn(), mods);
    }

    private static boolean canEdit(ActiveLogin activeLogin, String editDn) {
        return checkRightsLevel(activeLogin, editDn) >= 0;
    }

    protected static PersonList retrieve() throws NamingException {
        Hashtable<String, Object> env = config();

		DirContext ctx = new InitialDirContext(env);
		
		//Search controller
        SearchControls ctls = new SearchControls();

		//The actual search
		NamingEnumeration answer = ctx.search("ou=Users,dc=studentmediene,dc=no", "(&(memberOf=*))", ctls);

        ctx.close();
        return SearchProcessing.getPersons(answer);
	}
}