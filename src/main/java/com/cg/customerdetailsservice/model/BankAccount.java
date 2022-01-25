package com.cg.customerdetailsservice.model;

public class BankAccount {

    private String usereName;
    private String accountNo;

    public BankAccount() {
    }

    public BankAccount(String usereName, String accountNo) {
        this.usereName = usereName;
        this.accountNo = accountNo;
    }

    public String getUsereName() {
        return usereName;
    }

    public void setUsereName(String usereName) {
        this.usereName = usereName;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }
}
