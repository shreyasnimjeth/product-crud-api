package com.zest.productapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zest.productapi.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Integer>{


    //custom finder method 
    List<Item> findByProductId(Long productId);

}
