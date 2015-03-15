package com.springapp.mvc;

import com.springapp.mvc.model.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adrianh on 16.02.14.
 */
public class PersonList extends ArrayList<Person> {
    ArrayList<Person> personList = new ArrayList<Person>();

    public void addPerson(Person person) {
        this.personList.add(person);
    }

    public Person getPerson(Person person) {
        return this.personList.get(this.personList.indexOf(person));
    }

    public Person getPersonById(int id) {
        for (Person person : personList) {
            if (person.getUidNumber() == id) {
                return person;
            }
        }
        throw new IllegalArgumentException("No person with that id found");
    }

    public Person getPersonByUid(String uid) {
        for (Person person : personList) {
            if (person.getUid().equals(uid)) {
                return person;
            }
        }
        return null;
    }

    public void update(PersonList updateList) {
        for (int i = 0; i < updateList.size(); i++) {
            this.add(updateList.get(i));
        }
    }

    public void update(List<Person> list) {
        for (Person person : list) {
            this.add(person);
        }
    }

    public ArrayList<Person> getPersonList() {
        return this.personList;
    }
}
