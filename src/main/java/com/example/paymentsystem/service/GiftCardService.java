package com.example.paymentsystem.service;

import com.example.paymentsystem.model.GiftCard;

public interface GiftCardService extends CardService<GiftCard> {

    @Override
    GiftCard createCard(GiftCard card);
}
