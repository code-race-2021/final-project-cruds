package com.coderace.repository;

import com.coderace.model.entities.Delivery;
import com.coderace.model.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {
    Optional<Delivery> getByCode(String code);
}



