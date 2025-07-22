Architecture Overview

The system follows a modular microservices approach:

          [transaction-service]
                  |
              Kafka Topic
              'transactions'
                  ↓
         [processor-service]
                  |
              Kafka Topic
       'transactions-processed'
                  ↓
        [notification-service]

Each microservice is an independent Spring Boot app with a dedicated purpose.

Project Modules

This is a multi-module Maven project:

    transaction-service
        Accepts new transactions and publishes to Kafka topic transactions.
    processor-service
        Consumes from transactions, processes business logic, and produces to transactions-processed.
    notification-service
        Consumes from transactions-processed and logs a mock notification.

All modules are located in a single Git repository.

 
 Technologies Used

    Java 21

    Spring Boot 3.2+

    Spring Kafka

    Apache Kafka (tested with 3.x)

    Maven

🧾 Sample Transaction Flow

    POST a transaction to transaction-service:

    curl command:

    curl -X POST http://localhost:8081/transactions \
    -H "Content-Type: application/json" \
    -d '{
      "id": "txn001",
      "sender": "Sagar",
      "receiver": "Akshay",
      "amount": 10000000.0
    }'

    transaction-service sends message to Kafka topic transactions.

    processor-service consumes, logs or simulates processing, then sends to topic transactions-processed.

    notification-service receives from transactions-processed and logs a mock notification.

⚙️ How to Run Locally

 Prerequisites:

    Java 21

    Apache Kafka running locally (or Docker)

    Maven 3.8+

    Clone this repo:

git clone https://github.com/your-user/amex-transaction-system.git
cd amex-transaction-system

    Start Kafka locally (adjust ports as needed):

# Start Zookeeper
bin/zookeeper-server-start.sh config/zookeeper.properties

# Start Kafka Broker
bin/kafka-server-start.sh config/server.properties

Or use Docker:

docker-compose up -d

    Create Kafka topics (optional if auto-creation is disabled):

bin/kafka-topics.sh --create --topic transactions --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1

bin/kafka-topics.sh --create --topic transactions-processed --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1

    Run each service:

In three different terminals:

cd common-models && mvn clean install

cd ../transaction-service && mvn spring-boot:run
cd ../processor-service && mvn spring-boot:run
cd ../notification-service && mvn spring-boot:run

Each service will start on its configured port (e.g., 8081, 8082, 8083).

    Test the flow with curl (see above).

 Kafka Configuration Highlights

    Producer:

        spring.kafka.producer.value-serializer = JsonSerializer

        spring.kafka.producer.properties.spring.json.add.type.headers = false (to avoid class not found in other modules)

    Consumer:

        spring.kafka.consumer.value-deserializer = StringDeserializer

        Custom ObjectMapper is used to manually convert JSON to Transaction class

This setup avoids classpath issues and ensures clear, decoupled message passing.
📁 Folder Structure

.
├── transaction-service
│   └── RestController → KafkaTemplate → topic 'transactions'
├── processor-service
│   └── @KafkaListener('transactions') → process → send to 'transactions-processed'
├── notification-service
│   └── @KafkaListener('transactions-processed') → log


Notes

    This is a simulation — no actual financial transaction is executed.

    Kafka headers are avoided for schema safety across services.

    Error handling, logging, and retry logic can be expanded as needed.
