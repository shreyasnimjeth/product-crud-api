package com.zest.productapi.repository;

import com.zest.productapi.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldSaveAndFindProduct() {

        Product product = Product.builder()
                .productName("Test Product")
                .createdBy("testuser")
                .createdOn(LocalDateTime.now())
                .build();

        Product savedProduct =
                productRepository.save(product);

        assertThat(savedProduct.getId()).isNotNull();

        Product foundProduct =
                productRepository.findById(
                        savedProduct.getId()
                ).orElse(null);

        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.getProductName())
                .isEqualTo("Test Product");
    }
}