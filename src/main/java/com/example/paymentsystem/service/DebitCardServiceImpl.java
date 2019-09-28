package com.example.paymentsystem.service;

import com.example.paymentsystem.model.DebitCard;
import com.example.paymentsystem.repository.PaymentSystemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DebitCardServiceImpl implements DebitCardService {

    private PaymentSystemDao dao;

    @Autowired
    public DebitCardServiceImpl(PaymentSystemDao dao) {
        this.dao = dao;
    }

    @Override
    public DebitCard createCard(DebitCard card) {
        card.setCardType("DebitCard");
        return (DebitCard) dao.save(card);
    }
}
