package com.thoughtworks.ketsu.domain.users;

import com.thoughtworks.ketsu.infrastructure.mongo.mappers.OrderMapper;
import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

public class Order implements Record {
    private String id;
    private String userId;
    private String name;
    private String address;
    private String phone;
    private List<OrderItem> orderItems;

    @Inject
    OrderMapper orderMapper;

    public Order(String id, String userId, String name, String address, String phone, List<OrderItem> orderItems) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.orderItems = orderItems;
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

    @Override
    public Map<String, Object> toRefJson(Routes routes) {
        return new HashMap() {{
            put("uri", routes.orderUrl(userId, id));
            put("name", name);
            put("address", address);
            put("phone", phone);
            put("total_price", getTotalPrice());
            put("created_at", getCreatedAt().toString());
        }};
    }

    @Override
    public Map<String, Object> toJson(Routes routes) {
        Map<String, Object> map = toRefJson(routes);
        map.put("order_items", orderItems.stream().map(orderItem -> orderItem.toJson(routes)).collect(Collectors.toList()));
        return map;
    }

    public String getUserId() {
        return userId;
    }

    public Date getCreatedAt() {
        return new ObjectId(id).getDate();
    }

    public double getTotalPrice() {

        double total = 0;
        for (OrderItem orderItem : orderItems) {
            total += orderItem.getAmount() * orderItem.getQuantity();
        }
        return total;
    }

    public Payment pay(Map<String, Object> info) {
        return orderMapper.pay(info, id);
    }

    public Optional<Payment> getPayment() {
        return Optional.ofNullable(orderMapper.getPaymentOf(id));
    }
}
