package com.springapp.mvc;

import java.util.ArrayList;

/**
 * Created by adrianh on 16.02.14.
 */
public class PersonList extends ArrayList<Person> {
    ArrayList<Person> personList = new ArrayList<Person>();

    PersonList() {
        /*String[] smint = {"smint"};
        String[] it = {"it"};
        Person test = new Person("Test", "Testerson", "test@test.test", new ArrayList<String>(), 1);
        Person adrian = new Person("Adrian", "Hundseth", "adrian@smint.no", new ArrayList<String>(), 2);
        Person person = new Person("Person", "Norsep", "person@earth.com", new ArrayList<String>(), 3);
        Person person1 = new Person("Randy", "Inty", "rand@int.wat", new ArrayList<String>(), 4);
        this.personList.add(test);
        this.personList.add(adrian);
        this.personList.add(person);
        this.personList.add(person1);*/
    }

    public void addPerson(Person person) {
        this.personList.add(person);
    }

    public Person getPerson(Person person) {
        return this.personList.get(this.personList.indexOf(person));
    }

    public Person getPersonById(int id) {
        for (Person person : personList) {
            if (person.getId() == id) {
                return person;
            }
        }
        throw new IllegalArgumentException("No person with that id found");
    }

    public void update(PersonList updateList) {
        for (int i = 0; i < updateList.size(); i++) {
            this.add(updateList.get(i));
        }
    }

    public ArrayList<Person> getPersonList() {
        return this.personList;
    }
}
