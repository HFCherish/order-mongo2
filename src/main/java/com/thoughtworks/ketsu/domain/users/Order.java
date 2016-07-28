package com.thoughtworks.ketsu.domain.users;

import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order implements Record{
    private String id;
    private String userId;
    private String name;
    private String address;
    private String phone;
    private List<OrderItem> orderItems;
    private Date createdAt;

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

    @Override
    public Map<String, Object> toRefJson(Routes routes) {
        return new HashMap() {{
            put("uri", routes.orderUrl(userId, id));
            put("name", name);
            put("address", address);
            put("phone", phone);
            put("created_at", getCreatedAt().toString());
        }};
    }

    @Override
    public Map<String, Object> toJson(Routes routes) {
        return toRefJson(routes);
    }

    public Date getCreatedAt() {
        return new ObjectId(id).getDate();
    }
}
