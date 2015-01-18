package com.springapp.mvc;

import java.util.ArrayList;

/**
 * Created by adrianh on 12.02.14.
 */

public class Person {
    private String firstName;
    private String lastName;
    private String fullName;
    private String uid;
    private String email;
    private int mobile;
    protected ArrayList<String> groups = new ArrayList<String>();
    private int id;
    private String dn;
    private boolean harOblat;

    Person(String firstName, String lastName, String email, ArrayList<String> groups, int id, String dn, String userName, boolean harOblat) {
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setId(id);
        setDn(dn);
        setHarOblat(harOblat);
        setUid(userName);
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

    public String getDn() {
        return dn;
    }

    public ArrayList<String> getGroups() {
        return this.groups;
    }

    public String getMemberOf() {
        String memberOf = "";
        for (String s : groups) {
            memberOf += s;
        }
        return memberOf;
    }

    public String getUid() {
        return uid;
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

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int id) { this.id = id; }

    public void setDn(String dn) {
        this.dn = dn;
    }

    public void setMobile(int mobile) { this.mobile = mobile; }

    public void setGroups(ArrayList<String> groups) {
        this.groups = groups;
    }

    public void setHarOblat(boolean harOblat) {
        this.harOblat = harOblat;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", uid='" + uid + '\'' +
                ", email='" + email + '\'' +
                ", mobile=" + mobile +
                ", memberOf=" + groups +
                ", id=" + id +
                ", harOblat=" + harOblat +
                '}';
    }

}