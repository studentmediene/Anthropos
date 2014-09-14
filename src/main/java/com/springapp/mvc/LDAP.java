/*
For this to work, you have to run the program on the same LAN as the LDAP-server
or tunnel a port on your computer to the LDAP-server, for example:
ssh boyeborg@scgw1.studentmediene.no -L8389:ldap.studentmediene.local:389
*/
package com.springapp.mvc;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.Hashtable;


public class LDAP {
	
	private void localTest(){
		try {
			retrieve("ldap://localhost:8389", "uid=testuser,ou=System Users,dc=studentmediene,dc=no", "123qwerty", "*boye*", "*boye*", "*boye*");
		} catch (NamingException e) {
			System.out.println("Error");
		}
	}


	
	protected static PersonList retrieve(String host, String user, String password, String mail, String cn, String uid) throws NamingException{
		Hashtable<String, Object> env = new Hashtable<String, Object>();
		
		//Connection details
		env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, host);
		env.put(Context.SECURITY_AUTHENTICATION,"simple");
		env.put(Context.SECURITY_PRINCIPAL, user);
		env.put(Context.SECURITY_CREDENTIALS, password);
		
		DirContext ctx = new InitialDirContext(env);
		
		//Search controller
		SearchControls ctls = new SearchControls();
		
		//Example filter of search on 'boye' ('*' is a wildcard character)
		String filter = ("(|(mail=*" + mail + "*)(cn=*" + cn + "*)(uid=*" + uid + "*))");
		
		//The actual search
		NamingEnumeration answer = ctx.search("ou=Users,dc=studentmediene,dc=no", "(&(mail=*))", ctls);

		PersonList returnList = new PersonList();

        //Find the next person with while(answer.hasMore())
		for (int i = 0; i < 10; i++) {
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
			Attribute username = attributes.get("uid");
			Attribute fullName = attributes.get("cn");
			
			//For more efficiency, use try and catch the NullPointerException here
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
			if (username != null) {
				//System.out.println("Username: " + username.get());
                returnperson.setUserName("" + username.get());
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
			
			returnList.add(returnperson);
            System.out.print("added " + returnperson);
			System.out.println();
		}

        return returnList;
	}
	
}