package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.products.Product;
import com.thoughtworks.ketsu.domain.products.ProductRepository;
import com.thoughtworks.ketsu.domain.users.Order;
import com.thoughtworks.ketsu.domain.users.User;
import com.thoughtworks.ketsu.domain.users.UserRepository;
import com.thoughtworks.ketsu.support.ApiSupport;
import com.thoughtworks.ketsu.support.ApiTestRunner;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import java.util.List;
import java.util.Map;

import static com.thoughtworks.ketsu.support.TestHelper.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(ApiTestRunner.class)
public class OrdersApiTest extends ApiSupport {
    @Inject
    UserRepository userRepository;

    @Inject
    ProductRepository productRepository;
    private User user;
    private Product product;
    private String orderBaseUrl;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        user = prepareUser(userRepository);
        product = prepareProduct(productRepository);
        orderBaseUrl = "/users/" + user.getId() + "/orders";
    }

    @Test
    public void should_create_order() {
        Response response = post(orderBaseUrl, orderJsonForTest(product.getId()));

        assertThat(response.getStatus(), is(201));
        assertThat(response.getLocation().toString(), containsString(orderBaseUrl));
        assertThat(response.getLocation().toString().matches(".*/[a-zA-Z\\d]+$"), is(true));

    }

    @Test
    public void should_400_when_create_given_invalid_user_info() {
        Map<String, Object> info = orderJsonForTest(product.getId());
        //name empty
        info.remove("name");

        Response response = post(orderBaseUrl, info);

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void should_400_when_create_given_invalid_order_item_info() {
        Map<String, Object> info = orderJsonForTest(new ObjectId().toString());

        Response response = post(orderBaseUrl, info);

        assertThat(response.getStatus(), is(400));

    }

    @Test
    public void should_get_some_order() {
        Order order = prepareOrder(user,product);

        Response response = get(orderBaseUrl + "/" + order.getId());

        assertThat(response.getStatus(), is(200));
        Map info = response.readEntity(Map.class);
        assertThat(info.get("uri").toString(), containsString(orderBaseUrl + "/" + order.getId()));
        assertThat(info.get("name"), is(order.getName()));
        assertThat(info.get("address"), is(order.getAddress()));
        assertThat(info.get("phone"), is(order.getPhone()));
        assertThat((double)info.get("total_price"), is(order.getTotalPrice()));
        assertThat(info.get("created_at").toString(), is(order.getCreatedAt().toString()));

        List<Map> items = (List)info.get("order_items");
        assertThat(items.size(), is(1));
        assertThat(items.get(0).get("product_id"), is(product.getId()));
        assertThat((double)items.get(0).get("amount"), is(product.getPrice()));
        assertThat(items.get(0).get("uri").toString(), containsString("/products/" + product.getId()));
        assertThat(items.get(0).get("quantity"), is(order.getOrderItems().get(0).getQuantity()));
    }

    @Test
    public void should_404_when_get_given_not_exits() {
        Response response = get(orderBaseUrl + "/" + new ObjectId());

        assertThat(response.getStatus(), is(404));

    }

    @Test
    public void should_get_all_orders() {
        Order order = prepareOrder(user,product);

        Response response = get(orderBaseUrl);

        assertThat(response.getStatus(), is(200));

    }
}
