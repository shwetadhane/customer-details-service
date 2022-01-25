package com.cg.customerdetailsservice.model;


public class Customer {

    private String name;
    private String address;
    private String mobileNumber;
    private String accountNumber;

    public Customer() {
    }

    public Customer(String name, String address, String mobileNumber, String accountNumber) {
        this.name = name;
        this.address = address;
        this.mobileNumber = mobileNumber;
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
