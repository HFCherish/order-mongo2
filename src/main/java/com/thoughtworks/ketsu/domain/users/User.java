package com.thoughtworks.ketsu.domain.users;

import com.thoughtworks.ketsu.infrastructure.mongo.mappers.OrderMapper;
import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class User implements Record {
    private String id;
    private String name;

    @Inject
    OrderMapper orderMapper;

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
        return orderMapper.save(info, getId());
    }

    public Optional<Order> findOrderById(String orderId) {
        return Optional.ofNullable(orderMapper.findById(orderId));
    }

    public List<Order> findAllOrders() {
        return orderMapper.findAllOf(id);
    }
}
