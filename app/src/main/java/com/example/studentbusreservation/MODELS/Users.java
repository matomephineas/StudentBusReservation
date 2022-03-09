package com.example.studentbusreservation.MODELS;

public class Users {
    private String id,fullnames,email,phone,password,selectedCampusName,city,identity;
    private int usertype;

    public Users() {
    }

    public Users(String id, String fullnames, String email, String phone, String password, String selectedCampusName, String city, String identity, int usertype) {
        this.id = id;
        this.fullnames = fullnames;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.selectedCampusName = selectedCampusName;
        this.city = city;
        this.identity = identity;
        this.usertype = usertype;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullnames() {
        return fullnames;
    }

    public void setFullnames(String fullnames) {
        this.fullnames = fullnames;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSelectedCampusName() {
        return selectedCampusName;
    }

    public void setSelectedCampusName(String selectedCampusName) {
        this.selectedCampusName = selectedCampusName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public int getUsertype() {
        return usertype;
    }

    public void setUsertype(int usertype) {
        this.usertype = usertype;
    }
}
