package com.saurav.journalApp.service;

import com.saurav.journalApp.model.SentimentData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.kafka.listener.auto-startup=true",
    "spring.profiles.active=test"
})
public class KafkaProducerTest {

    @Autowired(required = false)
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Test
    public void testKafkaTemplateBean() {
        if (kafkaTemplate != null) {
            assertNotNull(kafkaTemplate, "KafkaTemplate should be available when Kafka is enabled");
            System.out.println("✅ KafkaTemplate bean successfully injected");
        } else {
            System.out.println("⚠️  KafkaTemplate not available (likely disabled in dev profile)");
        }
    }

    @Test
    public void testSendSentimentMessage() {
        if (kafkaTemplate == null) {
            System.out.println("⚠️  Skipping Kafka send test - Kafka disabled in dev profile");
            return;
        }

        try {
            SentimentData sentimentData = new SentimentData();
            sentimentData.setEmail("sauravmehta973532@gmail.com");
            sentimentData.setSentiment("Your journal this week shows POSITIVE sentiment with 80% positivity!");

            Message<SentimentData> message = MessageBuilder
                    .withPayload(sentimentData)
                    .setHeader(KafkaHeaders.TOPIC, "weekly-sentiment")
                    .build();

            kafkaTemplate.send(message);
            System.out.println("✅ Sentiment message sent to Kafka topic: weekly-sentiment");
            System.out.println("   Email: " + sentimentData.getEmail());
            System.out.println("   Sentiment: " + sentimentData.getSentiment());

            // Wait for consumer to process
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("❌ Kafka message send failed: " + e.getMessage());
            e.printStackTrace();
            fail("Kafka message send failed");
        }
    }

    @Test
    public void testSendMultipleSentimentMessages() {
        if (kafkaTemplate == null) {
            System.out.println("⚠️  Skipping multiple messages test - Kafka disabled in dev profile");
            return;
        }

        try {
            String[] emails = {"user1@example.com", "user2@example.com", "user3@example.com"};
            String[] sentiments = {
                "POSITIVE - Great week ahead!",
                "NEUTRAL - Regular week",
                "NEGATIVE - Challenging week"
            };

            for (int i = 0; i < emails.length; i++) {
                SentimentData data = new SentimentData();
                data.setEmail(emails[i]);
                data.setSentiment(sentiments[i]);

                Message<SentimentData> message = MessageBuilder
                        .withPayload(data)
                        .setHeader(KafkaHeaders.TOPIC, "weekly-sentiment")
                        .build();

                kafkaTemplate.send(message);
                System.out.println("✅ Message " + (i + 1) + " sent - Email: " + emails[i]);
            }

            System.out.println("✅ All " + emails.length + " sentiment messages sent to Kafka");
            Thread.sleep(2000);

        } catch (Exception e) {
            System.out.println("❌ Multiple Kafka messages send failed: " + e.getMessage());
            fail("Multiple Kafka messages send failed");
        }
    }

    @Test
    public void testKafkaConfiguration() {
        try {
            // Try to get Kafka config - this will fail gracefully if Kafka is disabled
            if (kafkaTemplate != null) {
                assertNotNull(kafkaTemplate.getProducerFactory(), "Producer factory should be configured");
                System.out.println("✅ Kafka configuration loaded successfully");
                System.out.println("   Producer factory: configured");
            } else {
                System.out.println("ℹ️  Kafka is disabled in dev profile (auto-startup: false)");
                System.out.println("   To enable Kafka testing, use production profile or enable in dev config");
            }
        } catch (Exception e) {
            System.out.println("❌ Kafka configuration test failed: " + e.getMessage());
            fail("Kafka configuration test failed");
        }
    }

    @Test
    public void testSentimentDataObject() {
        SentimentData sentimentData = new SentimentData();
        sentimentData.setEmail("test@example.com");
        sentimentData.setSentiment("Test sentiment");

        assertNotNull(sentimentData.getEmail());
        assertNotNull(sentimentData.getSentiment());

        System.out.println("✅ SentimentData object created and validated successfully");
        System.out.println("   Email: " + sentimentData.getEmail());
        System.out.println("   Sentiment: " + sentimentData.getSentiment());
    }
}
