package com.thoughtworks.ketsu.domain.users;

import java.util.List;

public class Order {
    private String id;
    private String userId;
    private String name;
    private String address;
    private String phone;
    private List<OrderItem> orderItems;

    public Order(String id, String userId, String name, String address, String phone) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
}
