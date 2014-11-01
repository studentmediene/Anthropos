package com.springapp.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.naming.NamingException;
import java.util.ArrayList;


@Controller
@RequestMapping("/")
public class JSONController {
    private PersonList personList = new PersonList();
    private ActiveLogin activeLogin;

    @RequestMapping("*")
    @ResponseBody
    public String fallbackMethod(){
        return "fallback method";
    }

    @RequestMapping
    public String defaultReturn() {
        return "index";
    }

    @RequestMapping(value="add", method=RequestMethod.POST)
    public @ResponseBody Person post(@RequestBody final Person person) {
        System.out.print("adding");
//        System.out.print(person.getId() + " " + person.getFirstName());
        personList.addPerson(person);
        System.out.print(person);
        return person;
    }

    @RequestMapping(value="/streng", method = RequestMethod.POST)
    public String test(String s) {
        System.out.print("Streng: " + s);
        return "/";
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
        System.out.print("Trying to get the list of users");
        try {
            System.out.println("Trying");
            System.out.println(LDAP.getDn("adem.ruud"));
            returnList.update(LDAP.retrieve());
        }
        catch (NamingException e) {
            System.out.print("Error: " + e.getMessage());
        }
        for (Person p : returnList) {
            ArrayList<String> groups = p.getGroups();
            ArrayList<String> sections = new ArrayList<String>();
            for (String group : groups) {
                if (group.contains("sections")) {
                    sections.add(group.substring(group.indexOf('=') + 1, group.indexOf(',')));
                }
            }
            p.setGroups(sections);
        }
        return returnList;
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public @ResponseBody ArrayList<Person> search(@RequestParam(value="name", required = true) String name) {
        PersonList returnList = new PersonList();
        System.out.println("Attempting search for: " + name);
        try {
            returnList.update(LDAP.search(name));
        } catch (NamingException e) {
            System.err.println("Search error: " + e.getMessage());
        }
        return returnList;
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public @ResponseBody String login(@RequestParam(value="uid", required = true) String uid, @RequestParam(value="cr", required = true) String cr) {
        System.out.println("UID: " + uid);
        System.out.println("PW: " + cr);
        try {
            activeLogin = new ActiveLogin(LDAP.getDn(uid), cr);
            if (activeLogin.getUid() != null) {
                LDAP.config(activeLogin);
                return "Success";
            } else {
                return "Failed";
            }
        } catch (NamingException e) {
            System.err.println("Login error: " + e.getMessage());
            return "Failed";
        }
    }

    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public @ResponseBody void edit(@RequestParam(value="uid", required = true) String uid, @RequestParam(value = "fields", required = true) ArrayList<String[]> fields) {
        try {
            LDAP.edit(activeLogin, fields);
        } catch (NamingException e) {
            System.err.println("Error");
        }
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

    @RequestMapping(value = "logout")
    public @ResponseBody void logout() {
        if (activeLogin == null) {
            System.out.println("No user is logged in");
        } else {
        System.out.println("Login out user: " + activeLogin.getUid());
        activeLogin = null;
        }
    }

    @RequestMapping(value = "forgotPassword")
    public @ResponseBody String forgotPassword() {
        //Code for sending new password to user here
        return "index";
    }
}