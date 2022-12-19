package com.example.mobileproject;



public class ReadWriteUserDetails {
    public String firstName, lastName, phone, address,image;

    public ReadWriteUserDetails(){

    };
    public ReadWriteUserDetails(String firstName, String lastName,String phone,String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
    }
    public ReadWriteUserDetails(String firstName, String lastName,String phone,String address,String image) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
        this.image = image;
    }
}
