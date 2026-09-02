package com.zest.productapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zest.productapi.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{



}
