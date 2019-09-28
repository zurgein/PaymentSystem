package com.example.paymentsystem.service;

import com.example.paymentsystem.model.CreditCard;

public interface CreditCardService extends CardService<CreditCard> {

    @Override
    CreditCard createCard(CreditCard card);
}
