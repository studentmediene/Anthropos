package com.springapp.mvc.controller;

import com.springapp.mvc.PersonList;
import com.springapp.mvc.authentication.LdapUserPwd;
import com.springapp.mvc.authentication.UserLoginService;
import com.springapp.mvc.ldap.LDAP;
import com.springapp.mvc.ldap.LdapUtil;
import com.springapp.mvc.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.naming.NamingException;
import java.util.ArrayList;


@Controller
@RequestMapping("/api")
public class JSONController {
    private PersonList personList = new PersonList();
    LdapUtil ldapUtil = new LdapUtil();

    @Autowired
    private UserLoginService userLoginService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("*")
    @ResponseBody
    public String fallbackMethod(){
        return "fallback method";
    }

    @RequestMapping
    public String defaultReturn() {
            return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody boolean login(@RequestBody LdapUserPwd ldapUserPwd) {
        System.out.println("login");
        logger.debug("Login try with: {}", ldapUserPwd);
        return userLoginService.login(ldapUserPwd);
    }

    @RequestMapping(value="add", method=RequestMethod.POST)
    public @ResponseBody
    Person post(@RequestBody final Person person) {
        return null;
    }

    @RequestMapping(value = "/addList", method = RequestMethod.POST)
    public @ResponseBody ArrayList<Person> listPost(@RequestBody final ArrayList<Person> list) {
        for (Person person : list) {
            personList.addPerson(person);
        }
        return personList.getPersonList();
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public @ResponseBody ArrayList<Person> getList() {
        PersonList returnList = new PersonList();
        System.out.print("Trying to get the list of users: ");
        try {
            System.out.println("Trying");
            System.out.println(LDAP.getDn("adem.ruud"));
            returnList.update(ldapUtil.getUsers());
        }
        catch (NamingException e) {
            System.out.print("Error: " + e.getMessage());
        }
        for (Person p : returnList) {
            ArrayList<String> groups = p.getMemberOf();
            ArrayList<String> sections = new ArrayList<String>();
            for (String group : groups) {
                if (group.contains("sections")) {
                    sections.add(group.substring(group.indexOf('=') + 1, group.indexOf(',')));
                }
            }
            p.setMemberOf(sections);
        }
        return returnList;
    }

    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public @ResponseBody void edit(@RequestParam(value="uid", required = true) String uid, @RequestParam(value = "fields", required = true) ArrayList<String[]> fields) {

    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public @ResponseBody Person getPersonById(@PathVariable int id) {
        Person person = null;
        try {
            person = LDAP.findByIdNumber(id);
        } catch(NamingException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return person;
    }

    @RequestMapping(value = "forgotPassword")
    public @ResponseBody String forgotPassword() {
        //Code for sending new password to user here
        return "index";
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public @ResponseBody ArrayList<Person> search(@RequestParam(value="name", required = true) String name) {
        PersonList returnList = new PersonList();
/*        System.out.println("Attempting search for: " + name);
        try {
            returnList.update(LDAP.search(name));
        } catch (NamingException e) {
            System.err.println("Search error: " + e.getMessage());
        }*/
        return returnList;
    }

    @RequestMapping(value="/streng", method = RequestMethod.POST)
    public String test(String s) {
        System.out.print("Streng: " + s);
        return "/";
    }

    @RequestMapping(value = "logout")
    public @ResponseBody void logout() {
        System.out.println("logout");
        userLoginService.logout();
    }
}