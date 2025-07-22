package com.amex.transactionservice.producer;

import com.amex.transactionservice.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionProducer {
    private static final String TOPIC = "transactions";

    @Autowired
    private KafkaTemplate<String, Transaction> kafkaTemplate;

    public void sendTransaction(Transaction transaction) {
        kafkaTemplate.send(TOPIC, transaction);
    }
}
