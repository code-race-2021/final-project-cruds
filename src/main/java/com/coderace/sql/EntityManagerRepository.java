package com.coderace.sql;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class EntityManagerRepository {
    @PersistenceContext
    private EntityManager em;

    public EntityManager em() {
        return em;
    }
}
