package com.thoughtworks.ketsu.support;

import com.thoughtworks.ketsu.domain.products.Product;
import com.thoughtworks.ketsu.domain.products.ProductRepository;

import java.util.HashMap;
import java.util.Map;

public class TestHelper {
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
}
