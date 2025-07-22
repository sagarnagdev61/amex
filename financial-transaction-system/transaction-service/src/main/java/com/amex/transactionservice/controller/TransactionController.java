package com.amex.transactionservice.controller;

import com.amex.transactionservice.model.Transaction;
import com.amex.transactionservice.producer.TransactionProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionProducer producer;

    @PostMapping
    public String sendTransaction(@RequestBody Transaction transaction) {
        producer.sendTransaction(transaction);
        return "Transaction sent!";
    }
}

