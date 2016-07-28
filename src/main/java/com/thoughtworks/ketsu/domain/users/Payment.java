package com.thoughtworks.ketsu.domain.users;

public class Payment {
    private PayType payType;
    private double amount;

    public PayType getPayType() {
        return payType;
    }

    public double getAmount() {
        return amount;
    }
}
