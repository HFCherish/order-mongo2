package com.thoughtworks.ketsu.domain.users;

import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Payment implements Record{
    private PayType payType;
    private double amount;
    private Order order;
    private Date createdAt;

    public Payment(PayType payType, double amount, Order order, Date createdAt) {
        this.payType = payType;
        this.amount = amount;
        this.order = order;
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() {
        return createdAt;
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

    @Override
    public Map<String, Object> toRefJson(Routes routes) {
        return new HashMap() {{
            put("order_uri", routes.orderUrl(order.getUserId(), order.getId()));
            put("uri", routes.paymentUrl(order.getUserId(), order.getId()));
            put("amount", amount);
            put("pay_type", payType);
            put("created_at", createdAt.toString());
        }};
    }

    @Override
    public Map<String, Object> toJson(Routes routes) {
        return toRefJson(routes);
    }
}
