package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.products.Product;
import com.thoughtworks.ketsu.domain.products.ProductRepository;
import com.thoughtworks.ketsu.support.ApiSupport;
import com.thoughtworks.ketsu.support.ApiTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import java.util.Map;

import static com.thoughtworks.ketsu.support.TestHelper.prepareProduct;
import static com.thoughtworks.ketsu.support.TestHelper.productJsonForTest;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(ApiTestRunner.class)
public class ProductApiTest extends ApiSupport {

    @Inject
    ProductRepository productRepository;

    private String productBaseUrl = "/products";

    @Test
    public void should_create_product() {
        Response response = post(productBaseUrl, productJsonForTest());

        assertThat(response.getStatus(), is(201));
        assertThat(response.getLocation().toString(), containsString(productBaseUrl));
        assertThat(response.getLocation().toString().matches(".*/[a-zA-Z\\d]+$"), is(true));
    }

    @Test
    public void should_400_when_create_given_incomplete_input() {
        Map<String, Object> info = productJsonForTest();
        //name empty
        info.remove("name");

        Response response = post(productBaseUrl, info);

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void should_get_some_product() {
        Product product = prepareProduct(productRepository);

        Response response = get(productBaseUrl + "/" + product.getId());

        assertThat(response.getStatus(), is(200));
        Map info = response.readEntity(Map.class);
        assertThat(info.get("uri").toString(), containsString(productBaseUrl + "/" + product.getId()));
        assertThat(info.get("name"), is(product.getName()));
        assertThat(info.get("description"), is(product.getDescription()));
        assertThat(info.get("_id"), is(product.getId()));
        assertThat((double)info.get("price"), is(product.getPrice()));
    }
}
