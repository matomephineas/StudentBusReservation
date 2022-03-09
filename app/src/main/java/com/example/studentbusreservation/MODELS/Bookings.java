package com.example.studentbusreservation.MODELS;

public class Bookings {
    private String bookingID,fullnames,identity,destination,time,date,seatNumber,count,busID;
    public Bookings() {
    }

    public Bookings(String bookingID, String fullnames, String identity, String destination, String time, String date, String seatNumber, String count, String busID) {
        this.bookingID = bookingID;
        this.fullnames = fullnames;
        this.identity = identity;
        this.destination = destination;
        this.time = time;
        this.date = date;
        this.seatNumber = seatNumber;
        this.count = count;
        this.busID = busID;
    }

    public String getBusID() {
        return busID;
    }

    public void setBusID(String busID) {
        this.busID = busID;
    }

    public String getBookingID() {
        return bookingID;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    public String getFullnames() {
        return fullnames;
    }

    public void setFullnames(String fullnames) {
        this.fullnames = fullnames;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}

