package com.thoughtworks.ketsu.Dao;

import com.google.inject.Injector;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.thoughtworks.ketsu.domain.users.Order;
import com.thoughtworks.ketsu.domain.users.OrderItem;
import com.thoughtworks.ketsu.infrastructure.mongo.mappers.OrderMapper;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderDao implements OrderMapper {

    private final DBCollection orderCollection;

    @Inject
    Injector injector;

    @Inject
    public OrderDao(DB db) {
        orderCollection = db.getCollection("orders");
    }

    @Override
    public Order save(Map<String, Object> info, String userId) {
        info.put("user_id", userId);
        orderCollection.insert(new BasicDBObject(info));
        Order order = buildOrder(orderCollection.findOne());
        injector.injectMembers(order);
        return order;
    }

    @Override
    public Order findById(String orderId) {
        DBObject dbObject = orderCollection.findOne(new BasicDBObject("_id", new ObjectId(orderId)));
        if (dbObject == null) return null;
        Order order = buildOrder(dbObject);
        injector.injectMembers(order);
        return order;
    }

    private Order buildOrder(DBObject object) {
        if (object == null) return null;

        return new Order(object.get("_id").toString(),
                object.get("user_id").toString(),
                object.get("name").toString(),
                object.get("address").toString(),
                object.get("phone").toString(),
                buildOrderItems(object));
    }

    private List<OrderItem> buildOrderItems(DBObject object) {
        List<OrderItem> items = new ArrayList<>();
        for (Map item : (List<Map>) object.get("order_items")) {
            items.add(new OrderItem(item.get("product_id").toString(),
                    (int) item.get("quantity")));
        }
        return items;
    }
}
