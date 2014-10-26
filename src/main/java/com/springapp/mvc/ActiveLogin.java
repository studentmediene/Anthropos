package com.springapp.mvc;

/**
 * Created by adrianh on 26.10.14.
 */
public class ActiveLogin {

    private String uid;
    private String cr;

    public ActiveLogin(String uid, String cr) {
        this.uid = uid;
        this.cr = cr;
    }

    public String getCr() {
        return this.cr;
    }

    public String getUid() {
        return this.uid;
    }
}
