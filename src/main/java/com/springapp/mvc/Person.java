package com.springapp.mvc;

/**
 * Created by adrianh on 12.02.14.
 */

public class Person {
    private String name;
    private String email;
    private int id;

    Person(String name, String email, int id) {
        setName(name);
        setEmail(email);
        setId(id);
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public int getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int id) { this.id = id; }
}
