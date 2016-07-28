package com.thoughtworks.ketsu.infrastructure.repositories;

import com.thoughtworks.ketsu.domain.products.ProductRepository;
import com.thoughtworks.ketsu.domain.users.Order;
import com.thoughtworks.ketsu.domain.users.Payment;
import com.thoughtworks.ketsu.domain.users.UserRepository;
import com.thoughtworks.ketsu.support.DatabaseTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import java.util.Optional;

import static com.thoughtworks.ketsu.support.TestHelper.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(DatabaseTestRunner.class)
public class PaymentOperationTest {
    @Inject
    UserRepository userRepository;

    @Inject
    ProductRepository productRepository;
    private Order order;

    @Before
    public void setUp() {
        order = prepareOrder(prepareUser(userRepository), prepareProduct(productRepository));
    }

    @Test
    public void should_save_and_get_payment() {
        Payment pay = order.pay(paymentJsonForTest());
        Optional<Payment> fetched = order.getPayment();

        assertThat(fetched.isPresent(), is(true));
        assertThat(fetched.get().getOrder().getId(), is(order.getId()));

    }
}
