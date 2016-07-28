package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.products.ProductRepository;
import com.thoughtworks.ketsu.domain.users.Order;
import com.thoughtworks.ketsu.domain.users.Payment;
import com.thoughtworks.ketsu.domain.users.UserRepository;
import com.thoughtworks.ketsu.support.ApiSupport;
import com.thoughtworks.ketsu.support.ApiTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import java.util.Map;

import static com.thoughtworks.ketsu.support.TestHelper.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(ApiTestRunner.class)
public class PaymentApiTest extends ApiSupport {
    @Inject
    UserRepository userRepository;
    @Inject
    ProductRepository productRepository;
    private Order order;
    private String paymentUrl;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        order = prepareOrder(prepareUser(userRepository), prepareProduct(productRepository));
        paymentUrl = "/users/" + order.getUserId() + "/orders/" + order.getId() + "/payment";
    }

    @Test
    public void should_pay() {
        Response response = post(paymentUrl, paymentJsonForTest());

        assertThat(response.getStatus(), is(201));
    }

    @Test
    public void should_400_when_pay_given_incomplete_input() {
        Map<String, Object> info = paymentJsonForTest();
        //amount empty
        info.remove("amount");

        Response response = post(paymentUrl, info);

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void should_get_pay() {
        Payment payment = preparePayment(order);
        Response response = get(paymentUrl);

        assertThat(response.getStatus(), is(200));
        Map info = response.readEntity(Map.class);
        assertThat(info.get("order_uri").toString(), containsString("/users/" + order.getUserId() + "/orders/" + order.getId()));
        assertThat(info.get("uri").toString(), containsString(paymentUrl));
        assertThat(info.get("pay_type").toString(), is(payment.getPayType().name()));
        assertThat((double)info.get("amount"), is(payment.getAmount()));
        assertThat(info.get("created_at").toString(), is(payment.getCreatedAt().toString()));
    }

    @Test
    public void should_404_when_get_given_not_exist() {
        Response response = get(paymentUrl);

        assertThat(response.getStatus(), is(404));

    }
}
