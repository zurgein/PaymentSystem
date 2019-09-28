package com.example.paymentsystem.repository;

import com.example.paymentsystem.model.Card;
import com.example.paymentsystem.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentSystemDao<T extends Card> extends JpaRepository<T, Long> {

}
