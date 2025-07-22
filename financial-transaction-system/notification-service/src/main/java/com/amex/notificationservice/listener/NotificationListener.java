package com.amex.notificationservice.listener;

import com.amex.notificationservice.model.Transaction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationListener {

    @KafkaListener(topics = "transactions-processed", groupId = "notifier-group")
    public void notify(String messageJson) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Transaction transaction = mapper.readValue(messageJson, Transaction.class);
        System.out.println("Notification: Transaction " + transaction.getId() +
            " completed for " + transaction.getAmount());
    }

}
