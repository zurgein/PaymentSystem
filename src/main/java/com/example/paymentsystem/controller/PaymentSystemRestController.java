package com.example.paymentsystem.controller;

import com.example.paymentsystem.model.CreditCard;
import com.example.paymentsystem.model.DebitCard;
import com.example.paymentsystem.model.GiftCard;
import com.example.paymentsystem.service.CreditCardService;
import com.example.paymentsystem.service.DebitCardService;
import com.example.paymentsystem.service.GiftCardService;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/card")
public class PaymentSystemRestController {

    private static final String INTERNAL_CARD_ID = "internalCardId";

    private DebitCardService debitCardService;
    private CreditCardService creditCardService;
    private GiftCardService giftCardService;

    @Autowired
    public PaymentSystemRestController(DebitCardService debitCardService, CreditCardService creditCardService, GiftCardService giftCardService) {
        this.debitCardService = debitCardService;
        this.creditCardService = creditCardService;
        this.giftCardService = giftCardService;
    }

    private Map<String, Object> customJsonObject = new LinkedHashMap<>();

    @JsonAnySetter
    private void setDetail(String key, Object value) {
        customJsonObject.put(key, value);
    }

    @PostMapping("/debitCard")
    public ResponseEntity createDebitCard(@Valid @RequestBody DebitCard card) {

        if(card.getInitialAmount() > 100 && (card.getExpirationDate().compareTo(new Date())) > 0) {
            DebitCard createdCard = debitCardService.createCard(card);
            setDetail(INTERNAL_CARD_ID, createdCard.getId());

            return ResponseEntity.status(HttpStatus.CREATED).body(customJsonObject);
        }

        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body("");
    }

    @PostMapping("/creditCard")
    public ResponseEntity createCreditCard(@Valid @RequestBody CreditCard card) {

        if(card.getInitialAmount() > 1000 && (card.getExpirationDate().compareTo(new Date())) > 0) {
            CreditCard createdCreditCard = creditCardService.createCard(card);
            setDetail(INTERNAL_CARD_ID,createdCreditCard.getId());

            return ResponseEntity.status(HttpStatus.CREATED).body(customJsonObject);
        }

        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body("");
    }

    @PostMapping("/giftCard")
    public ResponseEntity createGiftCard(@Valid @RequestBody GiftCard card) {

        if(card.getInitialAmount() <= 100 && (card.getExpirationDate().compareTo(new Date())) > 0) {
            GiftCard createdGiftCard = giftCardService.createCard(card);
            setDetail(INTERNAL_CARD_ID, createdGiftCard.getId());

            return ResponseEntity.status(HttpStatus.CREATED).body(customJsonObject);
        }

        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body("");
    }

}
