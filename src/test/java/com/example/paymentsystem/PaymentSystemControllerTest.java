package com.example.paymentsystem;

import com.example.paymentsystem.model.CreditCard;
import com.example.paymentsystem.model.DebitCard;
import com.example.paymentsystem.model.GiftCard;
import com.example.paymentsystem.service.CreditCardService;
import com.example.paymentsystem.service.DebitCardService;
import com.example.paymentsystem.service.GiftCardService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PaymentSystemControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    CreditCardService creditCardService;

    @MockBean
    DebitCardService debitCardService;

    @MockBean
    GiftCardService giftCardService;

    private static final String CREDIT_CARD_URL = "/card/creditCard";
    private static final String DEBIT_CARD_URL = "/card/debitCard";
    private static final String GIFT_CARD_URL = "/card/giftCard";

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void createCreditCard() {

        Date expirationDate = TestUtils.createFutureDate();
        Date createdAt = new Date();

        CreditCard creditCard = new CreditCard((long) 1,"123",expirationDate,1100, createdAt,"CreditCard");

        when(creditCardService.createCard(any(CreditCard.class))).thenReturn(creditCard);

        String creditCardJson = mapJavaObjectToJson(creditCard);

        //call with correct data
        performMvcCallWithCorrectData(creditCardJson, CREDIT_CARD_URL);

        //initial amount is lower than 1000
        creditCard.setInitialAmount(1);
        creditCardJson = mapJavaObjectToJson(creditCard);
        performMvcCallWithIncorrectData(creditCardJson, CREDIT_CARD_URL);

        //expiration Date < currentDate
        creditCard.setInitialAmount(2000);
        creditCard.setExpirationDate(new Date());
        creditCardJson = mapJavaObjectToJson(creditCard);
        performMvcCallWithIncorrectData(creditCardJson, CREDIT_CARD_URL);
    }

    @Test
    public void createDebitCard() {

        Date expirationDate = TestUtils.createFutureDate();
        Date createdAt = new Date();

        DebitCard debitCard = new DebitCard((long) 1,"321",expirationDate,101, createdAt,"DebitCard");

        when(debitCardService.createCard(any(DebitCard.class))).thenReturn(debitCard);

        String debitCardJson = mapJavaObjectToJson(debitCard);

        //call with correct data
        performMvcCallWithCorrectData(debitCardJson, DEBIT_CARD_URL);

        //initial amount is lower than 100
        debitCard.setInitialAmount(1);
        debitCardJson = mapJavaObjectToJson(debitCard);
        performMvcCallWithIncorrectData(debitCardJson, DEBIT_CARD_URL);

        //expiration Date < currentDate
        debitCard.setInitialAmount(2000);
        debitCard.setExpirationDate(new Date());
        debitCardJson = mapJavaObjectToJson(debitCard);
        performMvcCallWithIncorrectData(debitCardJson, DEBIT_CARD_URL);
    }

    @Test
    public void createGiftCard() {

        Date expirationDate = TestUtils.createFutureDate();
        Date createdAt = new Date();

        GiftCard giftCard = new GiftCard((long) 1,"321",expirationDate,99, createdAt,"GiftCard");

        when(giftCardService.createCard(any(GiftCard.class))).thenReturn(giftCard);

        String giftCardJson = mapJavaObjectToJson(giftCard);

        //call with correct data
        performMvcCallWithCorrectData(giftCardJson, GIFT_CARD_URL);

        //initial amount is bigger than 100
        giftCard.setInitialAmount(101);
        giftCardJson = mapJavaObjectToJson(giftCard);
        performMvcCallWithIncorrectData(giftCardJson, GIFT_CARD_URL);

        //expiration Date < currentDate
        giftCard.setInitialAmount(99);
        giftCard.setExpirationDate(new Date());
        giftCardJson = mapJavaObjectToJson(giftCard);
        performMvcCallWithIncorrectData(giftCardJson, GIFT_CARD_URL);
    }

    private String mapJavaObjectToJson(Object obj) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/yy");

        mapper.setDateFormat(formatter);

        String cardJson = null;
        try {
            cardJson = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            System.err.println("Error while converting object");
            e.printStackTrace();
        }

        return cardJson;
    }

    private void performMvcCallWithCorrectData(String jsonObject, String url) {
        try {
            mvc.perform(post(url)
                    .content(jsonObject)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.internalCardId").value("1"))
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void performMvcCallWithIncorrectData(String jsonObject, String url) {
        try {
            mvc.perform(post(url)
                    .content(jsonObject)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(content().string(""))
                    .andExpect(status().isPreconditionFailed())
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
