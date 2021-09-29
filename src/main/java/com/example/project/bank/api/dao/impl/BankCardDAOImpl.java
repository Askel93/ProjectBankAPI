package com.example.project.bank.api.dao.impl;

import com.example.project.bank.api.dao.BankCardDAO;
import com.example.project.bank.api.entity.BankCard;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class BankCardDAOImpl implements BankCardDAO {

    private final EntityManager entityManager;

    @Autowired
    public BankCardDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BankCard> findByBankAccountId(int bankAccountId) {
        Session session = entityManager.unwrap(Session.class);
        Query<BankCard> query = session.createQuery("from BankCard where bankAccount.id = :bankAccountId", BankCard.class);
        query.setParameter("bankAccountId", bankAccountId);

        return query.getResultList();
    }

    @Override
    @Transactional
    public BankCard save(BankCard bankCard) {
        return entityManager.merge(bankCard);
    }

    @Override
    @Transactional(readOnly = true)
    public BankCard findByNumber(String cardNumber) {
        javax.persistence.Query query = entityManager.createQuery("from BankCard where number = :number",BankCard.class);
        query.setParameter("number",cardNumber);
        List<BankCard> bankCardList = query.getResultList();

        return bankCardList.size() == 0 ? null : bankCardList.get(0);
    }

}
