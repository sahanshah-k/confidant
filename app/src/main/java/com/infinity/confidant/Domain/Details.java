package com.infinity.confidant.Domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Details implements Serializable {

    String mail;
    String name;
    String pin;
    int status;

    public Details() {
    }

    public Details(String pin, int status) {
        this.pin = pin;
        this.status = status;
    }

    public Details(String mail, String name, int status) {
        this.mail = mail;
        this.name = name;
        this.status = status;
    }

    public Details(String mail, String name, String pin, int status) {
        this.mail = mail;
        this.name = name;
        this.pin = pin;
        this.status = status;
    }


    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
