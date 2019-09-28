package com.example.paymentsystem.model;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class DebitCard extends Card {

    public DebitCard() {}

    public DebitCard (Long id, String cardNumber, Date expirationDate, double initialAmount, Date createdAt, String cardType) {
        super(id,cardNumber,expirationDate,initialAmount,createdAt,cardType);
    }

}
