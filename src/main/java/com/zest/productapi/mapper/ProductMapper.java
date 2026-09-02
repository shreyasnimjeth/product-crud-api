package com.zest.productapi.mapper;

import com.zest.productapi.dto.request.ProductRequest;
import com.zest.productapi.dto.response.ProductResponse;
import com.zest.productapi.entity.Product;

public class ProductMapper {

    private ProductMapper() {
    }

    public static Product toEntity(ProductRequest request) {
        Product product = new Product();
        product.setProductName(request.getProductName());
        return product;
    }

    public static ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .createdBy(product.getCreatedBy())
                .createdOn(product.getCreatedOn())
                .modifiedBy(product.getModifiedBy())
                .modifiedOn(product.getModifiedOn())
                .build();
    }

}
