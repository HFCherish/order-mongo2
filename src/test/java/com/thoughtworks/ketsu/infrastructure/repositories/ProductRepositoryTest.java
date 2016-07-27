package com.thoughtworks.ketsu.infrastructure.repositories;

import com.thoughtworks.ketsu.domain.products.Product;
import com.thoughtworks.ketsu.domain.products.ProductRepository;
import com.thoughtworks.ketsu.support.DatabaseTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import java.util.List;
import java.util.Optional;

import static com.thoughtworks.ketsu.support.TestHelper.prepareProduct;
import static com.thoughtworks.ketsu.support.TestHelper.productJsonForTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(DatabaseTestRunner.class)
public class ProductRepositoryTest {
    @Inject
    ProductRepository productRepository;

    @Test
    public void should_save_and_get_product() {
        Product product = productRepository.save(productJsonForTest());
        Optional<Product> fetched = productRepository.findById(product.getId());

        assertThat(fetched.isPresent(), is(true));
        assertThat(fetched.get().getId(), is(product.getId()));

    }

    @Test
    public void should_get_all_products() {
        Product product = prepareProduct(productRepository);

        List<Product> all = productRepository.findAll();

        assertThat(all.size(), is(1));
        assertThat(all.get(0).getId(), is(product.getId()));
    }
}
