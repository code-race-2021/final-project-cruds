package com.coderace.repository;

import com.coderace.model.entities.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExampleRepository extends JpaRepository<Example, Integer> {
    Optional<Example> getByLongValue(long longValue); // la firma alcanza para inferir la implementación
}
