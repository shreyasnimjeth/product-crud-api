package com.zest.productapi.service;

import com.zest.productapi.dto.request.ProductRequest;
import com.zest.productapi.dto.response.ProductResponse;
import com.zest.productapi.entity.Product;
import com.zest.productapi.exception.ResourceNotFoundException;
import com.zest.productapi.mapper.ProductMapper;
import com.zest.productapi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;


    //create product

    public ProductResponse createProduct(ProductRequest request, String username) {

        Product product = ProductMapper.toEntity(request);

        product.setCreatedBy(username);
        product.setCreatedOn(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);

        return ProductMapper.toResponse(savedProduct);
    }


    //get all product
    @Transactional(readOnly = true)
    public Page<ProductResponse> getAllProducts(Pageable pageable) {

        return productRepository.findAll(pageable).map(ProductMapper::toResponse);
    }


    //get prduct by id
    @Transactional(readOnly = true)
    public ProductResponse getProductById(int id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        return ProductMapper.toResponse(product);
    }


    //update product by id

    public ProductResponse updateProduct(
            int id,
            ProductRequest request,
            String username) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product with id " + id + " not found"
                        )
                );

        product.setProductName(request.getProductName());
        product.setModifiedBy(username);
        product.setModifiedOn(LocalDateTime.now());

        Product updatedProduct = productRepository.save(product);

        return ProductMapper.toResponse(updatedProduct);
    }

    //delete product by id

    public void deleteProduct(int id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product with id " + id + " not found"
                        )
                );

        productRepository.delete(product);
    }



}
