package com.thoughtworks.ketsu.Dao;

import com.google.inject.Injector;
import com.mongodb.*;
import com.thoughtworks.ketsu.domain.users.Order;
import com.thoughtworks.ketsu.domain.users.OrderItem;
import com.thoughtworks.ketsu.domain.users.PayType;
import com.thoughtworks.ketsu.domain.users.Payment;
import com.thoughtworks.ketsu.infrastructure.mongo.mappers.OrderMapper;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class OrderDao implements OrderMapper {

    private final DBCollection orderCollection;

    @Inject
    Injector injector;

    @Inject
    ProductDao productDao;

    @Inject
    public OrderDao(DB db) {
        orderCollection = db.getCollection("orders");
    }

    @Override
    public Order save(Map<String, Object> info, String userId) {
        info.put("user_id", userId);
        for (Map item : (List<Map>) info.get("order_items")) {
            item.put("amount", productDao.findById(item.get("product_id").toString()).getPrice());
        }
        orderCollection.insert(new BasicDBObject(info));
        return buildOrder(orderCollection.findOne());
    }

    @Override
    public Order findById(String orderId) {
        DBObject dbObject = orderCollection.findOne(new BasicDBObject("_id", new ObjectId(orderId)));
        if (dbObject == null) return null;

        return buildOrder(dbObject);
    }

    @Override
    public List<Order> findAllOf(String userId) {
        DBCursor objects = orderCollection.find(new BasicDBObject("user_id", userId));
        List<Order> orders = new ArrayList<>();
        while(objects.hasNext()) {
            orders.add(buildOrder(objects.next()));
        }
        return orders;
    }

    @Override
    public Payment getPaymentOf(String orderId) {
        return buildPayment(orderCollection.findOne(new BasicDBObject("_id", new ObjectId(orderId))));
    }

    @Override
    public Payment pay(Map<String, Object> info, String orderId) {
        info.put("created_at", new Date());
        BasicDBObject orderIdObj = new BasicDBObject("_id", new ObjectId(orderId));
        orderCollection.update(orderIdObj, new BasicDBObject("$set", new BasicDBObject(info)));
        return buildPayment(orderCollection.findOne(orderIdObj));
    }

    private Order buildOrder(DBObject object) {
        if (object == null) return null;

        Order order = new Order(object.get("_id").toString(),
                object.get("user_id").toString(),
                object.get("name").toString(),
                object.get("address").toString(),
                object.get("phone").toString(),
                buildOrderItems(object));
        injector.injectMembers(order);
        return order;
    }

    private List<OrderItem> buildOrderItems(DBObject object) {
        List<OrderItem> items = new ArrayList<>();
        for (Map item : (List<Map>) object.get("order_items")) {
            items.add(new OrderItem(item.get("product_id").toString(),
                            (int) item.get("quantity"),
                    (double) item.get("amount")));
        }
        return items;
    }

    private Payment buildPayment(DBObject object) {
        if(object == null)  return null;
        return new Payment(PayType.valueOf(object.get("pay_type").toString()),
                (double) object.get("amount"),
                buildOrder(object),
                (Date)object.get("created_at"));
    }
}
