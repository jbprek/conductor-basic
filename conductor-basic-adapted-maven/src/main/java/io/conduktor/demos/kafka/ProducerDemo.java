package io.conduktor.demos.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Scanner;

import java.util.Properties;
import java.util.stream.IntStream;

public class ProducerDemo {

    private static final Logger log = LoggerFactory.getLogger(ProducerDemo.class.getSimpleName());

    public static void main(String[] args) {
        log.info("I am a Kafka Producer!");

        // create Producer Properties
        Properties properties = new Properties();

        // connect to Localhost
        properties.setProperty("bootstrap.servers", "localhost:9092");

        // connect to Conduktor Playground
        //  properties.setProperty("bootstrap.servers", "cluster.playground.cdkt.io:9092");
        // properties.setProperty("security.protocol", "SASL_SSL");
        // properties.setProperty("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"your-username\" password=\"your-password\";");
        // properties.setProperty("sasl.mechanism", "PLAIN");

        // set producer properties
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());

        // create the Producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.print("Messsage to send: ");
            String message = scanner.nextLine();
            if (message.equals("exit"))
                break;
            // create a Producer Record
            ProducerRecord<String, String> producerRecord =
                    new ProducerRecord<>("test-topic", message);

            // send data
            producer.send(producerRecord);

            // tell the producer to send all data and block until done -- synchronous
            producer.flush();
        }
        // flush and close the producer
        producer.close();
    }
}
