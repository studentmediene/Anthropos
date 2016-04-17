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

package no.smint.anthropos.model;

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
    private String telephoneNumber;
    public ArrayList<String> memberOf = new ArrayList<String>();
    private int uidNumber;
    private String dn;
    private boolean harOblat;

    public Person(String givenName, String sn, String mail, ArrayList<String> memberOf, String telephoneNumber, int uidNumber, String uid, boolean harOblat) {
        setGivenName(givenName);
        setSn(sn);
        setMail(mail);
        setUidNumber(uidNumber);
        setHarOblat(harOblat);
        setTelephoneNumber(telephoneNumber);
        setUid(uid);
        setUidNumber(uidNumber);
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

    public int getUidNumber() {
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

    public String getTelephoneNumber() { return telephoneNumber; }

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

    public void setUidNumber(int uidNumber) { this.uidNumber = uidNumber; }

    public void setDn(String dn) {
        this.dn = dn;
    }

    public void setTelephoneNumber(String telephoneNumber) { this.telephoneNumber = telephoneNumber; }

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