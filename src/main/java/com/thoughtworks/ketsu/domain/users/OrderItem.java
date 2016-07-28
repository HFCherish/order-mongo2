package com.thoughtworks.ketsu.domain.users;

import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;

import java.util.HashMap;
import java.util.Map;

public class OrderItem implements Record{
    private String prodId;
    private int quantity;

    public OrderItem(String prodId, int quantity) {
        this.prodId = prodId;
        this.quantity = quantity;
    }

    public String getProdId() {
        return prodId;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public Map<String, Object> toRefJson(Routes routes) {
        return new HashMap() {{
            put("product_id", prodId);
            put("quantity", quantity);
        }};
    }

    @Override
    public Map<String, Object> toJson(Routes routes) {
        return toRefJson(routes);
    }
}
