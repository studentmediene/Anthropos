package com.springapp.mvc;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by adrianh on 16.02.14.
 */
public class PersonList extends ArrayList<Person> {
    ArrayList<Person> personList = new ArrayList<Person>();

    PersonList() {
        Person test = new Person("Test", "Testerson", "test@test.test", "smint", 1);
        Person adrian = new Person("Adrian", "Hundseth", "adrian@smint.no", "smint", 2);
        Person person = new Person("Person", "Norsep", "person@earth.com", "smint", 3);
        Person person1 = new Person("Randy", "Inty", "rand@int.wat", "IT", 4);
        this.personList.add(test);
        this.personList.add(adrian);
        this.personList.add(person);
        this.personList.add(person1);
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

    public ArrayList<Person> getPersonList() {
        return this.personList;
    }
}
