package com.example.studentbusreservation.MODELS;
public class Bus {
    private String busID,seats,name,reg_number,city;

    public Bus() {
    }

    public Bus(String busID, String seats, String name, String reg_number, String city) {
        this.busID = busID;
        this.seats = seats;
        this.name = name;
        this.reg_number = reg_number;
        this.city = city;
    }

    public String getBusID() {
        return busID;
    }

    public void setBusID(String busID) {
        this.busID = busID;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReg_number() {
        return reg_number;
    }

    public void setReg_number(String reg_number) {
        this.reg_number = reg_number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
