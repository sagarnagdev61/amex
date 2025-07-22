package com.amex.processorservice.listener;

import com.amex.processorservice.model.Transaction;
import com.amex.processorservice.producer.ProcessedTransactionProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TransactionListener {

    @Autowired
    private ProcessedTransactionProducer producer;

    @KafkaListener(topics = "transactions", groupId = "processor-group", containerFactory = "kafkaListenerContainerFactory")
    public void process(Transaction transaction) {
        System.out.println("Processor received: " + transaction.getId());
        transaction.setStatus("PROCESSED");
        producer.sendProcessed(transaction);
    }
}
