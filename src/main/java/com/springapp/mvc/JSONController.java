package com.springapp.mvc;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Created by adrianh on 12.02.14.
 */

@Controller
@RequestMapping("/")
public class JSONController {
    private PersonList personList = new PersonList();

    @RequestMapping
    public String defaultReturn() {
        return "index";
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public @ResponseBody Person post(@RequestBody final Person person) {
        System.out.print("adding");
        System.out.print(person.getId() + " " + person.getFirstName());
        personList.addPerson(person);
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
        System.out.print("list");
        return personList.getPersonList();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public @ResponseBody Person getPersonById(@PathVariable int id) {
        return personList.getPersonById(id);
    }

    @RequestMapping(value = "/memberOf/{membership}", method = RequestMethod.GET)
    public @ResponseBody ArrayList<Person> personsByMembership(@PathVariable String groups) {
        ArrayList<Person> retList = new ArrayList<Person>();
        for (Person person : personList.getPersonList()) {
            for (String group : person.getGroups()) {
                if (group.compareTo(groups) == 0) {
                    retList.add(person);
                }
            }

        }
        return retList;
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public @ResponseBody String listUsersJson() throws JSONException {
        JSONArray userArray = new JSONArray();
        for (Person user : personList.getPersonList()) {
            JSONObject userJSON = new JSONObject();
            userJSON.put("id", user.getId());
            userJSON.put("name", user.getFirstName());
            userJSON.put("lastname", user.getLastName());
            userJSON.put("email", user.getEmail());
            userArray.put(userJSON);
        }
        return userArray.toString();
    }
}