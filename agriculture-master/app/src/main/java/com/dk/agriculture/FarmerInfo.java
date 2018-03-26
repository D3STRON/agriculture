package com.dk.agriculture;

public class FarmerInfo {

    public String name, location;

    public FarmerInfo() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public FarmerInfo(String name, String location) {

        this.name = name;
        this.location = location;
    }
}
