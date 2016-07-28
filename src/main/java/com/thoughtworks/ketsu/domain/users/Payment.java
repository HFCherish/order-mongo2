package com.thoughtworks.ketsu.domain.users;

public class Payment {
    private PayType payType;
    private double amount;
    private Order order;

    public Payment(PayType payType, double amount, Order order) {
        this.payType = payType;
        this.amount = amount;
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    public PayType getPayType() {
        return payType;
    }

    public double getAmount() {
        return amount;
    }
}
