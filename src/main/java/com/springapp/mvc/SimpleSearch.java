//package com.springapp.mvc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.ldap.core.LdapTemplate;

import java.util.HashSet;
import java.util.Set;
/*
public class SimpleSearch {

    private LdapTemplate ldapTemplate;
    @SuppressWarnings("unchecked")
    public Set getAllUsers(){

        UserAttributesMapper mapper = new UserAttributesMapper();
        return new HashSet(
            ldapTemplate.search("ou=users,ou=system", "(objectClass=person)", mapper));
    }

    public void setLdapTemplate(LdapTemplate ldapTemplate){
        this.ldapTemplate = ldapTemplate;
    }

    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("search.xml");

        SimpleSearch simpleSearch = new SimpleSearch();
        simpleSearch.setLdapTemplate(context.getBean("ldapTemplate", LdapTemplate.class));
        for (Person person : simpleSearch.getAllUsers()){
            System.out.println(person);
        }
    }
}
*/