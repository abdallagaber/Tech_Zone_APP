package com.example.mobileproject;



public class ReadWriteUserDetails {
    public String firstName, lastName, phone, DOB, address;

    public ReadWriteUserDetails(){

    };
    public ReadWriteUserDetails(String firstName, String lastName,String phone,String DOB,String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.DOB =  DOB;
        this.address = address;

    }
}
