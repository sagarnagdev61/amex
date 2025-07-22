package com.amex.processorservice.producer;

import com.amex.processorservice.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProcessedTransactionProducer {

    private static final String TOPIC = "transactions-processed";

    @Autowired
    private KafkaTemplate<String, Transaction> kafkaTemplate;

    public void sendProcessed(Transaction transaction) {
        kafkaTemplate.send(TOPIC, transaction);
    }
}
