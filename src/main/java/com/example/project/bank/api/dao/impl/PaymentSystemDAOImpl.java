package com.example.project.bank.api.dao.impl;

import com.example.project.bank.api.entity.PaymentSystem;
import com.example.project.bank.api.dao.PaymentSystemDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
public class PaymentSystemDAOImpl implements PaymentSystemDAO {
    private final EntityManager entityManager;

    @Autowired
    public PaymentSystemDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentSystem findById(int id) {
        return entityManager.find(PaymentSystem.class, id);
    }
}


