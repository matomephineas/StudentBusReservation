package com.example.studentbusreservation.MODELS;

public class Assigned {
    private String name,reg,clock,id,seats,province,destination;
    private int count,availableSeats;

    public Assigned() {
    }

    public Assigned(String name, String reg, String clock, String id, String seats, String province, String destination, int count, int availableSeats) {
        this.name = name;
        this.reg = reg;
        this.clock = clock;
        this.id = id;
        this.seats = seats;
        this.province = province;
        this.destination = destination;
        this.count = count;
        this.availableSeats = availableSeats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReg() {
        return reg;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }

    public String getClock() {
        return clock;
    }

    public void setClock(String clock) {
        this.clock = clock;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }
}
