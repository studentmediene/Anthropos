package com.springapp.mvc;

import java.util.ArrayList;

/**
 * Created by adrianh on 16.02.14.
 */
public class PersonList {
    ArrayList<Person> personList = new ArrayList<Person>();

    PersonList() {
        Person test = new Person("Test", "test@test.test", 1);
        this.personList.add(test);
    }

    public void addPerson(Person person) {
        this.personList.add(person);
    }

    public Person getPerson(Person person) {
        return this.personList.get(this.personList.indexOf(person));
    }

    public Person getPersonById(int id) {
        for (int i = 0; i < personList.size(); i++) {
            if (this.personList.get(i).getId() == id) {
                return this.personList.get(i);
            }
        }
        throw new IllegalArgumentException("No person with that id found");
    }
}
