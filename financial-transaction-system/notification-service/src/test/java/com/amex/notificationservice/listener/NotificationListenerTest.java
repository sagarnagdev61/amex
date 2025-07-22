package com.amex.notificationservice.listener;

import com.amex.notificationservice.model.Transaction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.annotation.KafkaListener;
import static org.mockito.Mockito.*;




class NotificationListenerTest {

    private NotificationListener notificationListener;

    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        notificationListener = new NotificationListener();
    }

    @Test
    void testNotify() throws JsonProcessingException {
        // Arrange
        String messageJson = "{\"id\": \"123\", \"amount\": \"100.00\"}";
        Transaction transaction = new Transaction();
        transaction.setId("123");
        transaction.setAmount("100.00");

        NotificationListener listener = new NotificationListener();
        listener.notify(messageJson);
    }
}