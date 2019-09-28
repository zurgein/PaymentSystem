package com.example.paymentsystem.service;

import com.example.paymentsystem.model.GiftCard;
import com.example.paymentsystem.repository.PaymentSystemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GiftCardServiceImpl implements GiftCardService {

    private PaymentSystemDao dao;

    @Autowired
    public GiftCardServiceImpl(PaymentSystemDao dao) {
        this.dao = dao;
    }

    @Override
    public GiftCard createCard(GiftCard card) {
        card.setCardType("GiftCard");
        return (GiftCard) dao.save(card);
    }
}
