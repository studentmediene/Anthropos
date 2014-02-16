package com.springapp.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by adrianh on 12.02.14.
 */

@Controller
@RequestMapping("/json")
public class JSONController {
    private PersonList personList = new PersonList();

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    Person getPerson() {
        Person person = personList.getPersonById(1);
//        person.setName("Test");
//        person.setEmail("test@test.test");
//        person.setId(1);
        return person;
    }

    @RequestMapping(value = "person", method = RequestMethod.POST)
    public @ResponseBody Person post(@RequestBody final Person person) {
        System.out.print(person.getId() + " " + person.getName());
        return person;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody Person getPersonById(@PathVariable("id") int id) {
        return personList.getPersonById(id);
    }
}


