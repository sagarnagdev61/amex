package com.amex.processorservice.config;

import com.amex.processorservice.model.Transaction;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;





class KafkaConsumerConfigTest {

    private final KafkaConsumerConfig kafkaConsumerConfig = new KafkaConsumerConfig();

    @Test
    void testConsumerFactory() {
        ConsumerFactory<String, Transaction> consumerFactory = kafkaConsumerConfig.consumerFactory();
        assertNotNull(consumerFactory, "ConsumerFactory should not be null");

        Map<String, Object> configs = consumerFactory.getConfigurationProperties();
        assertEquals("localhost:9092", configs.get(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG), "Bootstrap servers should match");
        assertEquals("processor-group", configs.get(ConsumerConfig.GROUP_ID_CONFIG), "Group ID should match");
        assertEquals(StringDeserializer.class, configs.get(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG), "Key deserializer should match");
        assertEquals(JsonDeserializer.class, configs.get(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG).getClass(), "Value deserializer should match");

        JsonDeserializer<Transaction> deserializer = (JsonDeserializer<Transaction>) configs.get(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG);
        assertNotNull(deserializer, "JsonDeserializer should not be null");
        assertEquals(Transaction.class, deserializer.getTargetType(), "Deserializer target type should match Transaction class");
    }
}