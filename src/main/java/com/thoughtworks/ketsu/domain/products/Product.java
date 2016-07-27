package com.thoughtworks.ketsu.domain.products;

public class Product {
    private String name;
    private String id;
    private String description;
    private double price;

    public Product(String id, String name, String description, double price) {
        this.name = name;
        this.id = id;
        this.description = description;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }
}
