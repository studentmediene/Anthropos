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

/*
For this to work, you have to run the program on the same LAN as the LDAP-server
or tunnel a port on your computer to the LDAP-server, for example:
ssh boyeborg@scgw1.studentmediene.no -L8389:ldap.studentmediene.local:389
*/
package no.smint.anthropos.ldap;

import org.springframework.stereotype.Service;


/**
 * This class is a collection of static functions used to interact with the LDAP server.
 * @author Adrian Hundseth
 */
@Service
@Deprecated
public class LDAP {

/*    @Autowired
    LdapTemplate ldapTemplate;

    Logger logger = LoggerFactory.getLogger(getClass());*/

    //private static final String host = "ldap://localhost:8389"; //For testing on the real server. Must tunnel to scgwl.studentmediene.no
    /*
        For this to work, you have to run the program on the same LAN as the LDAP-server
        or tunnel a port on your computer to the LDAP-server, for example:
        ssh boyeborg@scgw1.studentmediene.no -L8389:ldap.studentmediene.local:389
    */

/*
    private static final String name = "ou=Users,dc=studentmediene,dc=no";

    private static final String host = "ldap://ldapstaging.studentmediene.no";
*/

    /*public PersonList getUsers() {
        final int[] activeCount = {0};
        PersonList personList = new PersonList();
        logger.info("ldapTemplate context source: " + ldapTemplate.getContextSource());
        List<Person> persons = ldapTemplate.search("ou=Users", "(objectClass=person)", new AttributesMapper<Person>() {
            @Override
            public Person mapFromAttributes(Attributes attributes) throws NamingException {
                Long uidNumber = Long.valueOf((String) attributes.get("uidNumber").get());
                String givenName = attributes.get("givenName") != null ? (String) attributes.get("givenName").get() : "";
                String cn = attributes.get("cn") != null ? (String) attributes.get("cn").get() : "";
                String uid = attributes.get("uid") != null ? (String) attributes.get("uid").get() : "";
                String mail = attributes.get("mail") != null ? (String) attributes.get("mail").get() : "";
                String telephoneNumber = attributes.get("telephoneNumber") != null ? (String) attributes.get("telephoneNumber").get() : "";
                boolean isActive = false;

                Attribute memberOf1 = attributes.get("memberOf");
                ArrayList<String> memberOf = new ArrayList<String>();
                if (memberOf1 != null) {
                    NamingEnumeration<?> memberOf2 = memberOf1.getAll();
                    while (memberOf2.hasMore()) {
                        memberOf.add((String) memberOf2.next());
                    }
                }

                return new Person(givenName, cn, mail, memberOf, telephoneNumber, uidNumber, uid, false);
            }
        });
        logger.info("Number of users from LDAP: {}", persons.size());
        logger.info("Number of active: {}", activeCount[0]);

        personList.update(persons);
        return personList;
    }*/
/*
    *//**
     * Binds anonymously to the LDAP server. Returns a <code>Hashtable</code> to use for searching etc.
     * @return <code>Hashtable</code> with the binding to the server.
     *//*
    public static Hashtable<String, Object> config() {
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
    }*/

/*    *//*
     * Receives a <code>DistinguishedName</code> in the form of an <code>ActiveLogin</code> object (i.e. uid=firstname.lastname,ou=Users,dc=studentmediene,dc=no) and a password.
     * It then binds the provided username to the server. It returns the bind in the form of a <code>Hashtable</code>.
     * Throws a <code>NamingException</code> if the username is not found on the server.
     * @param activeLogin An object containing the <code>DistinguishedName</code> and the credentials
     * @return Returns a <code>Hashtable</code> of the binding to the server.
     * @throws NamingException Thrown if the <code>DistinguishedName</code> is not found on the server
     *//*
    public static Hashtable<String, Object> config(ActiveLogin activeLogin) throws NamingException {
        Hashtable<String, Object> env = new Hashtable<String, Object>();

        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, host);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, activeLogin.getDn());
        env.put(Context.SECURITY_CREDENTIALS, activeLogin.getCr());

        return env;
    }*/

    /*
     * Searches the server for the supplied <code>String</code>. Searches mail, name and <code>DistinguishedName</code>.
     * <p>
     *     Uses the {@link SearchProcessing#getPersons(javax.naming.NamingEnumeration)} function in {@link SearchProcessing}
     * </p>
     * <p>
     *     Returns a <code>PersonList</code> object with all the users matching the search terms.
     *     Throws a <code>NamingException</code> received from the {@link LDAP#config(ActiveLogin)} function.
     * </p>
     * @param search The <code>String</code> to search for.
     * @return Returns a <code>PersonList</code> object with <code>Person</code> objects corresponding to the search term
     * @throws NamingException Thrown upwards by the <code>config</code> function
     * @see SearchProcessing
     * @see PersonList
     */
    /*public static PersonList search(String search) throws NamingException {
        Hashtable<String, Object> env = config();
        DirContext ctx = new InitialDirContext(env);
        SearchControls ctls = new SearchControls();
        String filter = ("(|(mail=*" + search + "*)(cn=*" + search + "*)(dn=*" + search + "*))");
        NamingEnumeration answer = ctx.search(name, filter, ctls);

        return SearchProcessing.getPersons(answer);
    }*/

    /*
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
     /*public static int checkRightsLevel(ActiveLogin activeLogin, String editDn) {
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
            for (String group : person.getMemberOf()) {
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
            for (String group : editPerson.getMemberOf()) {
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
    }*/

    /*public static String getDn(String uid) throws NamingException {
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
    }*/

    /*public static Person findByIdNumber(int id) throws NamingException {
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
    }*/


/*    public static void addAsEdit(Person user) throws NamingException {
        String editDn = getDn(user.getUid());

        ActiveLogin activeLogin = new ActiveLogin(getDn("birgith.do"), "overrated rapid machine");

        Hashtable<String, Object> env = config(activeLogin);

        DirContext ctx = new InitialDirContext(env);

        ArrayList<String[]> fields = new ArrayList<String[]>();
        String[] givenName = {"givenName", user.getGivenName()};
        String[] sn = {"sn", user.getSn()};
        String[] mail = {"mail", user.getMail()};
        String[] mobile = {"telephoneNumber", user.getTelephoneNumber()};
        //String[] groups = {"memberOf", user.getMemberOf()};
        //fields.add(givenName);
        //fields.add(sn);
        fields.add(mail);
        //fields.add(telephoneNumber);
        //fields.add(memberOf);

        ModificationItem[] mods = new ModificationItem[fields.size()];
        for (String[] field : fields) {
            Attribute mod = new BasicAttribute(field[0], field[1]);
            mods[fields.indexOf(field)] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod);
        }
        ctx.modifyAttributes(editDn, mods);
    }*/

    /*public static void edit(ActiveLogin activeLogin, String editDn, ArrayList<String[]> fields) throws NamingException {
        //if (canEdit(activeLogin, editDn)) {
        //}
        Hashtable<String, Object> env = config(activeLogin);

        DirContext ctx = new InitialDirContext(env);

        ModificationItem[] mods = new ModificationItem[fields.size()];
        for (String[] field : fields) {
            Attribute mod = new BasicAttribute(field[0], field[1]);
            mods[fields.indexOf(field)] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod);
        }
        ctx.modifyAttributes(activeLogin.getDn(), mods);
    }*/

    /*private static boolean canEdit(ActiveLogin activeLogin, String editDn) {
        return checkRightsLevel(activeLogin, editDn) >= 0;
    }*/

    /*public static PersonList retrieve() throws NamingException {
        Hashtable<String, Object> env = config();

		DirContext ctx = new InitialDirContext(env);
		
		//Search controller
        SearchControls ctls = new SearchControls();

		//The actual search
		NamingEnumeration answer = ctx.search("ou=Users,dc=studentmediene,dc=no", "(&(memberOf=*))", ctls);

        ctx.close();
        return SearchProcessing.getPersons(answer);
	}*/
}



