package com.example.rec.myads_fragments;

public class product {
    private int id;
    private String location;
    private String description;
    private int image;

    public product(int id, String location, String description, int image) {
        this.id = id;
        this.location = location;
        this.description = description;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public int getImage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(int image) {
        this.image = image;
    }
}

