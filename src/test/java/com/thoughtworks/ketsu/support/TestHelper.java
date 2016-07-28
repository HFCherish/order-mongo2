package com.thoughtworks.ketsu.support;

import com.thoughtworks.ketsu.domain.products.Product;
import com.thoughtworks.ketsu.domain.products.ProductRepository;
import com.thoughtworks.ketsu.domain.users.Order;
import com.thoughtworks.ketsu.domain.users.User;
import com.thoughtworks.ketsu.domain.users.UserRepository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TestHelper {

    public static final String USER_NAME = "Petrina";
    public static final String INVALID_USER_NAME = "Petrina';L'";

    public static Map<String, Object> productJsonForTest() {
        return new HashMap() {{
            put("name", "Imran");
            put("description", "teacher");
            put("price", 678.1);
        }};
    }

    public static Product prepareProduct(ProductRepository productRepository) {
        return productRepository.save(productJsonForTest());
    }

    public static Map<String, Object> paymentJsonForTest() {
        return new HashMap() {{
            put("pay_type", "CASH");
            put("amount", 798.9);
        }};
    }

    public static Map<String, Object> orderJsonForTest(String prodId) {
        return new HashMap() {{
            put("name", "Petrina");
            put("address", "beijing");
            put("phone", "7689");
            put("order_items", Arrays.asList(new HashMap() {{
                put("product_id", prodId);
                put("quantity", 2);
            }}));
        }};
    }

    public static Order prepareOrder(User user, Product product) {
        return user.placeOrder(orderJsonForTest(product.getId()));
    }

    public static Map<String, Object> userJsonForTest(String name) {
        return new HashMap() {{
            put("name", name);
        }};
    }

    public static User prepareUser(UserRepository userRepository) {
        return userRepository.save(userJsonForTest(USER_NAME));
    }
}
