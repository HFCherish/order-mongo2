package com.thoughtworks.ketsu.Dao;

import com.mongodb.*;
import com.thoughtworks.ketsu.domain.products.Product;
import com.thoughtworks.ketsu.infrastructure.mongo.mappers.ProductMapper;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductDao implements ProductMapper {

    private final DBCollection prodCollection;


    @Inject
    public ProductDao(DB db) {
        prodCollection = db.getCollection("products");
    }

    @Override
    public Product save(Map<String, Object> info) {
        prodCollection.insert(new BasicDBObject(info));
        return buildProduct(prodCollection.findOne());
    }

    @Override
    public Product findById(String productId) {
        return buildProduct(prodCollection.findOne(new BasicDBObject("_id", new ObjectId(productId))));
    }

    @Override
    public List<Product> findAll() {
        DBCursor prodObjs = prodCollection.find();
        List<Product> products = new ArrayList<>();
        while(prodObjs.hasNext()) {
            products.add(buildProduct(prodObjs.next()));
        }
        return products;
    }

    private Product buildProduct(DBObject object) {
        if(object == null) return null;

        return new Product(object.get("_id").toString(),
                object.get("name").toString(),
                object.get("description").toString(),
                (double) object.get("price"));
    }
}
