package com.thoughtworks.ketsu.infrastructure.repositories;

import com.thoughtworks.ketsu.domain.products.Product;
import com.thoughtworks.ketsu.domain.products.ProductRepository;
import com.thoughtworks.ketsu.domain.users.Order;
import com.thoughtworks.ketsu.domain.users.User;
import com.thoughtworks.ketsu.domain.users.UserRepository;
import com.thoughtworks.ketsu.support.DatabaseTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import java.util.Optional;

import static com.thoughtworks.ketsu.support.TestHelper.orderJsonForTest;
import static com.thoughtworks.ketsu.support.TestHelper.prepareProduct;
import static com.thoughtworks.ketsu.support.TestHelper.prepareUser;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(DatabaseTestRunner.class)
public class OrderOperationTest  {
    @Inject
    UserRepository userRepository;

    @Inject
    ProductRepository productRepository;
    private User user;
    private Product product;

    @Before
    public void setUp() {
        user = prepareUser(userRepository);
        product = prepareProduct(productRepository);
    }

    @Test
    public void should_create_and_get_product() {
        Order order = user.placeOrder(orderJsonForTest(product.getId()));
        Optional<Order> fetched = user.findOrderById(order.getId());

        assertThat(fetched.isPresent(), is(true));
        assertThat(fetched.get().getId(), is(order.getId()));
    }
}
