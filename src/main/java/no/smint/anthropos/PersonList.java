/*
 * Copyright 2016 Studentmediene i Trondheim AS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package no.smint.anthropos;

import no.smint.anthropos.model.Person;

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
