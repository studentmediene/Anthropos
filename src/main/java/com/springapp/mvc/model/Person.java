package com.springapp.mvc.model;

import java.util.ArrayList;

/**
 * Created by adrianh on 12.02.14.
 */

public class Person {
    private String givenName;
    private String sn;
    private String cn;
    private String uid;
    private String mail;
    private int telephoneNumber;
    public ArrayList<String> memberOf = new ArrayList<String>();
    private Long uidNumber;
    private String dn;
    private boolean harOblat;

    public Person(String givenName, String sn, String mail, ArrayList<String> memberOf, Long uidNumber, String dn, String uid, boolean harOblat) {
        setGivenName(givenName);
        setSn(sn);
        setMail(mail);
        setUidNumber(uidNumber);
        setDn(dn);
        setHarOblat(harOblat);
        setUid(uid);
        setMemberOf(memberOf);
    }

    public Person(){
        return;
    }

    public String getGivenName() {
        return this.givenName;
    }

    public String getSn() {
        return this.sn;
    }

    public String getMail() {
        return this.mail;
    }

    public String getCn() {
        return this.cn;
    }

    public Long getUidNumber() {
        return this.uidNumber;
    }

    public String getDn() {
        return dn;
    }

    public ArrayList<String> getMemberOf() {
        return memberOf;
    }

    public String getMemberOfAsString() {
        String memberOf = "";
        for (String s : this.memberOf) {
            memberOf += s;
        }
        return memberOf;
    }

    public String getUid() {
        return uid;
    }

    public int getTelephoneNumber() { return telephoneNumber; }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setUidNumber(Long uidNumber) { this.uidNumber = uidNumber; }

    public void setDn(String dn) {
        this.dn = dn;
    }

    public void setTelephoneNumber(int telephoneNumber) { this.telephoneNumber = telephoneNumber; }

    public void setMemberOf(ArrayList<String> memberOf) {
        this.memberOf = memberOf;
    }

    public void setHarOblat(boolean harOblat) {
        this.harOblat = harOblat;
    }

    @Override
    public String toString() {
        return "Person{" +
                "givenName='" + givenName + '\'' +
                ", sn='" + sn + '\'' +
                ", cn='" + cn + '\'' +
                ", uid='" + uid + '\'' +
                ", mail='" + mail + '\'' +
                ", telephoneNumber=" + telephoneNumber +
                ", memberOf=" + memberOf +
                ", uidNumber=" + uidNumber +
                ", harOblat=" + harOblat +
                '}';
    }

}