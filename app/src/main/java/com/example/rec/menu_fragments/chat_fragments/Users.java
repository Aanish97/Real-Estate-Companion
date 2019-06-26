package com.example.rec.menu_fragments.chat_fragments;

import com.example.rec.User;

public class Users extends User {

    String name;
    String image;
    String contact;
    String id;

    public Users(String name, String image, String contact, String id) {
        this.name = name;
        this.image = image;
        this.contact = contact;
        this.id=id;
    }

    public Users()
    {}

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

}
