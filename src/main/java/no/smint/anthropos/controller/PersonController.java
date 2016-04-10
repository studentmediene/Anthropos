package no.smint.anthropos.controller;

import no.smint.anthropos.PersonList;
import no.smint.anthropos.authentication.AuthUserDetails;
import no.smint.anthropos.authentication.LdapUserPwd;
import no.smint.anthropos.authentication.UserLoginService;
import no.smint.anthropos.authentication.UserLoginServiceImpl;
import no.smint.anthropos.ldap.LDAP;
import no.smint.anthropos.ldap.LdapUtil;
import no.smint.anthropos.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.naming.NamingException;
import java.util.ArrayList;


@Controller
@RequestMapping("/")
public class PersonController {
    private PersonList personList = new PersonList();
    LdapUtil ldapUtil = new LdapUtil();

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private UserLoginServiceImpl userLoginServiceImpl;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody void login(@RequestBody LdapUserPwd ldapUserPwd) {
        logger.debug("Login try with: {}", ldapUserPwd);
        userLoginService.login(ldapUserPwd);
    }

    @RequestMapping(value = "logout")
    public @ResponseBody void logout() {
        System.out.println("logout");
        userLoginService.logout();
    }

    @RequestMapping(value = "me")
    public @ResponseBody Person me() {
        AuthUserDetails loggedInUser = userLoginServiceImpl.getLoggedInUserDetails();
        logger.debug("Current user: {}", loggedInUser.getUsername());

        try {
            return LDAP.findByIdNumber(loggedInUser.getUidNumber().intValue());
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return null;
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

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody ArrayList<Person> getList() {
        PersonList returnList = new PersonList();
        System.out.print("Trying to get the list of users: ");
        returnList.update(ldapUtil.getUsers());
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

    @RequestMapping(value = "forgotPassword")
    public @ResponseBody String forgotPassword() {
        //Code for sending new password to user here
        return "index";
    }

    @Deprecated
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

    @RequestMapping(value = "/addList", method = RequestMethod.POST)
    public @ResponseBody ArrayList<Person> listPost(@RequestBody final ArrayList<Person> list) {
        for (Person person : list) {
            personList.addPerson(person);
        }
        return personList.getPersonList();
    }
}