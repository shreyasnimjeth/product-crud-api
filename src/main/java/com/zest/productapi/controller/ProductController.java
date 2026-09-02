package com.zest.productapi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Sort;
import com.zest.productapi.dto.request.ProductRequest;
import com.zest.productapi.dto.response.ProductResponse;
import com.zest.productapi.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {


    private final ProductService productService;

// create product    

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request,
        Authentication authentication) 
    {

        ProductResponse response = productService.createProduct(request, authentication.getName());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

// get all productas

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Page<ProductResponse>> getAllProducts(@PageableDefault(size = 10, sort = "id",  direction = Sort.Direction.DESC) Pageable pageable) 
    {

                return ResponseEntity.ok(productService.getAllProducts(pageable));
    }

//get product by id

     @GetMapping("/{id}")
     @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable int id) {

        return ResponseEntity.ok(productService.getProductById(id));
    }


    //update product by id

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable int id, @Valid @RequestBody ProductRequest request, Authentication authentication) {
        
        return ResponseEntity.ok(productService.updateProduct(id,request,authentication.getName()));
    }


    //delete product by id

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {

        productService.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }
}
