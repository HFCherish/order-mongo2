package com.thoughtworks.ketsu.Dao;

import com.google.inject.Injector;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.thoughtworks.ketsu.domain.users.User;
import com.thoughtworks.ketsu.infrastructure.mongo.mappers.UserMapper;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import java.util.Map;

public class UserDao implements UserMapper {

    private final DBCollection userCollection;

    @Inject
    Injector injector;

    @Inject
    public UserDao(DB db) {
        userCollection = db.getCollection("users");
    }

    @Override
    public User save(Map<String, Object> info) {
        userCollection.insert(new BasicDBObject(info));
        DBObject user = userCollection.findOne();
        injector.injectMembers(user);
        return buildUser(user);
    }

    @Override
    public User findById(String id) {

        DBObject userObj = userCollection.findOne(new BasicDBObject("_id", new ObjectId(id)));
        User user = buildUser(userObj);
        if(user == null) return null;
        injector.injectMembers(user);
        return user;
    }

    private User buildUser(DBObject object) {
        if (object == null) return null;
        return new User(object.get("_id").toString(),
                object.get("name").toString());
    }
}
