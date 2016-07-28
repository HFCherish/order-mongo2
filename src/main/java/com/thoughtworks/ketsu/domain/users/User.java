package com.thoughtworks.ketsu.domain.users;

import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class User implements Record {
    private String id;
    private String name;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public Map<String, Object> toRefJson(Routes routes) {
        return new HashMap(){{
            put("uri", routes.userUrl(getId()));
            put("name", getName());
            put("_id", getId());
        }};
    }

    @Override
    public Map<String, Object> toJson(Routes routes) {
        return toRefJson(routes);
    }

    public Order placeOrder(Map<String, Object> info) {
        return null;
    }

    public Optional<Order> findOrderById(String orderId) {
        return Optional.ofNullable(new Order());
    }
}
