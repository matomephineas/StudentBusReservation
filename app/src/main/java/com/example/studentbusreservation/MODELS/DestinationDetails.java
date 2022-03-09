package com.example.studentbusreservation.MODELS;

public class DestinationDetails {
    private String destID,name,city,type;

    public DestinationDetails() {
    }

    public DestinationDetails(String destID, String name, String city, String type) {
        this.destID = destID;
        this.name = name;
        this.city = city;
        this.type = type;
    }

    public String getDestID() {
        return destID;
    }

    public void setDestID(String destID) {
        this.destID = destID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
