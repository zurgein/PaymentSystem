package com.example.paymentsystem.service;

import com.example.paymentsystem.model.DebitCard;

public interface DebitCardService extends CardService<DebitCard> {

    @Override
    DebitCard createCard(DebitCard card);
}
