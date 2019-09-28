package com.example.paymentsystem;

import com.example.paymentsystem.model.Card;
import com.example.paymentsystem.model.CreditCard;
import com.example.paymentsystem.model.DebitCard;
import com.example.paymentsystem.model.GiftCard;
import com.example.paymentsystem.repository.PaymentSystemDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PaymentSystemDaoTest {

    @Autowired
    PaymentSystemDao dao;

    @Test
    public void persistCreditCard() {

        Date expirationDate = TestUtils.createFutureDate();
        Date createdAt = new Date();

        CreditCard creditCard = new CreditCard((long) 1,"123",expirationDate,1100, createdAt,"CreditCard");

        dao.save(creditCard);

        CreditCard card = (CreditCard) dao.getOne((long)1);
        assertEquals("123", card.getCardNumber());
        assertEquals(expirationDate, card.getExpirationDate());
        assertEquals(1100, card.getInitialAmount(), 0);
        assertEquals("CreditCard", card.getCardType());



        CreditCard creditCard2 = new CreditCard((long) 2,"1234",expirationDate,1200, createdAt,"CreditCard");
        dao.save(creditCard2);
        List<CreditCard> cards = dao.findAll();
        assertEquals(2,cards.size());
    }

    @Test
    public void persistDebitCard() {

        Date expirationDate = TestUtils.createFutureDate();
        Date createdAt = new Date();

        DebitCard creditCard = new DebitCard((long) 3,"321",expirationDate,1100, createdAt,"DebitCard");

        dao.save(creditCard);

        DebitCard card = (DebitCard) dao.getOne((long)3);
        assertEquals("321", card.getCardNumber());
        assertEquals(expirationDate, card.getExpirationDate());
        assertEquals(1100, card.getInitialAmount(), 0);
        assertEquals("DebitCard", card.getCardType());

        DebitCard debitCard2 = new DebitCard((long) 4,"4321",expirationDate,1100, createdAt,"DebitCard");
        dao.save(debitCard2);

        List<DebitCard> cards = dao.findAll();
        assertEquals(2,cards.size());
    }

    @Test
    public void persistGiftCard() {

        Date expirationDate = TestUtils.createFutureDate();
        Date createdAt = new Date();

        GiftCard giftCard = new GiftCard((long) 5,"12345",expirationDate,99, createdAt,"GiftCard");

        dao.save(giftCard);

        GiftCard card = (GiftCard) dao.getOne((long)5);
        assertEquals("12345", card.getCardNumber());
        assertEquals(expirationDate, card.getExpirationDate());
        assertEquals(99, card.getInitialAmount(), 0);
        assertEquals("GiftCard", card.getCardType());

        GiftCard giftCard2 = new GiftCard((long) 6,"64321",expirationDate,98, createdAt,"GiftCard");
        dao.save(giftCard2);

        List<GiftCard> cards = dao.findAll();
        assertEquals(2,cards.size());
    }

}

