package com.coderace.repository;

import com.coderace.model.entities.Example;
import com.coderace.model.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> getBySku(String sku);
}
