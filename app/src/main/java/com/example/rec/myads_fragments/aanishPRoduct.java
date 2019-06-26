package com.example.rec.myads_fragments;

public class aanishPRoduct {

    String uid;
    String area;
    String demand;
    String description;
    String location;
    String siteType;
    String type;

    public aanishPRoduct()
    {

    }
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDemand() {
        return demand;
    }

    public void setDemand(String demand) {
        this.demand = demand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSiteType() {
        return siteType;
    }

    public void setSiteType(String siteType) {
        this.siteType = siteType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public aanishPRoduct(String uid, String area, String demand, String description, String location, String siteType, String type) {
        this.uid = uid;
        this.area = area;
        this.demand = demand;
        this.description = description;
        this.location = location;
        this.siteType = siteType;
        this.type = type;
    }
}
