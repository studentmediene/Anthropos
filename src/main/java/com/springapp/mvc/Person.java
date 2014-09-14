package com.springapp.mvc;

import java.util.ArrayList;

/**
 * Created by adrianh on 12.02.14.
 */

public class Person {
    private String firstName;
    private String lastName;
    private String fullName;
    private String userName;
    private String email;
    private int mobile;
    protected ArrayList<String> groups = new ArrayList<String>();
    private int id;

    Person(String firstName, String lastName, String email, ArrayList<String> groups, int id) {
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setId(id);
        setGroups(groups);
    }

    Person(){
        return;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getFullName() {
        return this.fullName;
    }

    public int getId() {
        return this.id;
    }

    public ArrayList<String> getGroups() {
        return this.groups;
    }

    public String getUserName() {
        return userName;
    }

    public int getMobile() { return mobile; }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int id) { this.id = id; }

    public void setMobile(int mobile) { this.mobile = mobile; }

    public void setGroups(ArrayList<String> groups) {
        this.groups = groups;
    }

    public String toString(Person person) {
        return "{'firstname':'" + person.getFirstName() + "','lastname':" + person.getLastName() + "','username':" + person.getUserName() + "','email':" + person.getEmail() + "',''mobile':" + person.getMobile() + "','id':" + person.getId() + "'}";
    }
}
