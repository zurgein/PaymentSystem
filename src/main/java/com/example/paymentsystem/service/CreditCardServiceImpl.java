package com.example.paymentsystem.service;

import com.example.paymentsystem.model.CreditCard;
import com.example.paymentsystem.repository.PaymentSystemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditCardServiceImpl implements CreditCardService {

    private PaymentSystemDao dao;

    @Autowired
    public CreditCardServiceImpl(PaymentSystemDao dao) {
        this.dao = dao;
    }

    @Override
    public CreditCard createCard(CreditCard card) {
        card.setCardType("CreditCard");
        return (CreditCard) dao.save(card);
    }
}
