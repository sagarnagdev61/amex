package com.amex.transactionservice.config;

import com.amex.transactionservice.model.Transaction;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;





class KafkaProducerConfigTest {

    @Test
    void testProducerFactoryConfiguration() {
        KafkaProducerConfig kafkaProducerConfig = new KafkaProducerConfig();
        ProducerFactory<String, Transaction> producerFactory = kafkaProducerConfig.producerFactory();

        assertNotNull(producerFactory, "ProducerFactory should not be null");
        assertTrue(producerFactory instanceof DefaultKafkaProducerFactory, "ProducerFactory should be an instance of DefaultKafkaProducerFactory");

        Map<String, Object> configs = ((DefaultKafkaProducerFactory<String, Transaction>) producerFactory).getConfigurationProperties();
        assertEquals("localhost:9092", configs.get(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG), "Bootstrap servers configuration should match");
        assertEquals(StringSerializer.class, configs.get(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG), "Key serializer configuration should match");
        assertEquals(JsonSerializer.class, configs.get(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG), "Value serializer configuration should match");
    }
}