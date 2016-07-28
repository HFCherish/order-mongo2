package com.thoughtworks.ketsu.domain.users;

import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;

import java.util.HashMap;
import java.util.Map;

public class Payment implements Record{
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

    @Override
    public Map<String, Object> toRefJson(Routes routes) {
        return new HashMap() {{
            put("order_uri", routes.orderUrl(order.getUserId(), order.getId()));
            put("uri", routes.paymentUrl(order.getUserId(), order.getId()));
            put("amount", amount);
            put("pay_type", payType);
        }};
    }

    @Override
    public Map<String, Object> toJson(Routes routes) {
        return toRefJson(routes);
    }
}
