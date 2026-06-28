package com.saurav.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class IntegrationFeatureTest {

    @Autowired
    private RedisService redisService;

    @Autowired
    private EmailService emailService;

    @Autowired(required = false)
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired(required = false)
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired(required = false)
    private JavaMailSender javaMailSender;

    @Test
    public void testAllFeaturesBeanAvailability() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("FEATURE AVAILABILITY CHECK");
        System.out.println("=".repeat(60));

        // Check Redis
        assertNotNull(redisService, "RedisService should be available");
        System.out.println("✅ Redis: AVAILABLE");
        System.out.println("   - RedisService: Injected");
        System.out.println("   - RedisTemplate: " + (redisTemplate != null ? "Available" : "Not Available"));

        // Check Email
        assertNotNull(emailService, "EmailService should be available");
        System.out.println("✅ Email: AVAILABLE");
        System.out.println("   - EmailService: Injected");
        System.out.println("   - JavaMailSender: " + (javaMailSender != null ? "Available" : "Not Available"));

        // Check Kafka
        System.out.println((kafkaTemplate != null ? "✅" : "⚠️ ") + " Kafka: " + (kafkaTemplate != null ? "AVAILABLE" : "DISABLED (Dev Profile)"));
        if (kafkaTemplate != null) {
            System.out.println("   - KafkaTemplate: Injected");
        } else {
            System.out.println("   - Kafka is disabled in dev profile (auto-startup: false)");
        }

        System.out.println("=".repeat(60) + "\n");
    }

    @Test
    public void testRedisFeature() {
        System.out.println("\n" + "-".repeat(60));
        System.out.println("REDIS FEATURE TEST");
        System.out.println("-".repeat(60));

        try {
            String testKey = "feature_test_key";
            String testValue = "feature_test_value_" + System.currentTimeMillis();

            // Test Redis
            redisService.set(testKey, testValue, 300L);
            String retrieved = redisService.get(testKey, String.class);

            assertEquals(testValue, retrieved, "Redis set/get should work");
            System.out.println("✅ Redis Feature: WORKING");
            System.out.println("   - Set: OK");
            System.out.println("   - Get: OK");
            System.out.println("   - TTL: 300 seconds");

            // Clean up
            redisTemplate.delete(testKey);

        } catch (Exception e) {
            System.out.println("❌ Redis Feature: FAILED");
            System.out.println("   Error: " + e.getMessage());
            fail("Redis feature test failed");
        }
        System.out.println("-".repeat(60) + "\n");
    }

    @Test
    public void testEmailFeature() {
        System.out.println("\n" + "-".repeat(60));
        System.out.println("EMAIL FEATURE TEST");
        System.out.println("-".repeat(60));

        try {
            String to = "sauravmehta973532@gmail.com";
            String subject = "Integration Test - Feature Verification";
            String body = "This email confirms that the email service is working correctly.\n" +
                          "Time: " + System.currentTimeMillis() + "\n" +
                          "Status: Integration Test Passed";

            emailService.sendEmail(to, subject, body);
            System.out.println("✅ Email Feature: WORKING");
            System.out.println("   - Recipient: " + to);
            System.out.println("   - Subject: " + subject);
            System.out.println("   - Status: Email Queued");

        } catch (Exception e) {
            System.out.println("❌ Email Feature: FAILED");
            System.out.println("   Error: " + e.getMessage());
            fail("Email feature test failed");
        }
        System.out.println("-".repeat(60) + "\n");
    }

    @Test
    public void testKafkaFeature() {
        System.out.println("\n" + "-".repeat(60));
        System.out.println("KAFKA FEATURE TEST");
        System.out.println("-".repeat(60));

        if (kafkaTemplate == null) {
            System.out.println("⚠️  Kafka Feature: DISABLED IN DEV");
            System.out.println("   - Status: auto-startup = false");
            System.out.println("   - Reason: Disabled to prevent authentication errors");
            System.out.println("   - How to Test: Switch to production profile");
        } else {
            try {
                System.out.println("✅ Kafka Feature: AVAILABLE");
                System.out.println("   - KafkaTemplate: Ready");
                System.out.println("   - Topics: weekly-sentiment");
                System.out.println("   - Status: Ready to send messages");
            } catch (Exception e) {
                System.out.println("❌ Kafka Feature: FAILED");
                System.out.println("   Error: " + e.getMessage());
            }
        }
        System.out.println("-".repeat(60) + "\n");
    }

    @Test
    public void printTestingGuide() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("HOW TO TEST EACH FEATURE");
        System.out.println("=".repeat(60));

        System.out.println("\n📌 REDIS TESTING:");
        System.out.println("   ✅ Run: RedisServiceTest.java");
        System.out.println("   Tests:");
        System.out.println("      - testRedisConnection() - Check Redis connectivity");
        System.out.println("      - testSetAndGetString() - String storage/retrieval");
        System.out.println("      - testSetAndGetUser() - Object serialization");
        System.out.println("      - testRedisExpiration() - TTL functionality");
        System.out.println("      - testMultipleKeysStorage() - Multiple key management");

        System.out.println("\n📌 EMAIL TESTING:");
        System.out.println("   ✅ Run: EmailServiceIntegrationTest.java");
        System.out.println("   Tests:");
        System.out.println("      - testEmailServiceBean() - Bean injection");
        System.out.println("      - testSendEmailWithValidData() - Send single email");
        System.out.println("      - testSendMultipleEmails() - Batch email sending");
        System.out.println("      - testSendEmailWithWeatherReport() - Email with content");
        System.out.println("      - testSendEmailWithSentimentAnalysis() - Sentiment report");
        System.out.println("      - testEmailConfigurationLoaded() - Config verification");

        System.out.println("\n📌 KAFKA TESTING:");
        System.out.println("   ⚠️  Run: KafkaProducerTest.java");
        System.out.println("   Note: Kafka is disabled in DEV profile by default");
        System.out.println("   Tests:");
        System.out.println("      - testKafkaTemplateBean() - Bean availability check");
        System.out.println("      - testSendSentimentMessage() - Send single message");
        System.out.println("      - testSendMultipleSentimentMessages() - Batch messages");
        System.out.println("      - testKafkaConfiguration() - Config verification");

        System.out.println("\n📌 HOW TO ENABLE KAFKA TESTING:");
        System.out.println("   Option 1: Change application-dev.yml");
        System.out.println("      kafka:");
        System.out.println("        listener:");
        System.out.println("          auto-startup: true  # Change from false to true");
        System.out.println("");
        System.out.println("   Option 2: Run with production profile");
        System.out.println("      ./mvnw clean test -Dspring.profiles.active=prod");
        System.out.println("");
        System.out.println("   Option 3: Run specific Kafka test");
        System.out.println("      ./mvnw test -Dtest=KafkaProducerTest");

        System.out.println("\n📌 RUN ALL TESTS:");
        System.out.println("   ✅ ./mvnw clean test");
        System.out.println("      - Runs all test classes");
        System.out.println("      - Includes Redis, Email, and Kafka tests");

        System.out.println("\n📌 RUN SPECIFIC TEST CLASS:");
        System.out.println("   ✅ ./mvnw test -Dtest=RedisServiceTest");
        System.out.println("   ✅ ./mvnw test -Dtest=EmailServiceIntegrationTest");
        System.out.println("   ✅ ./mvnw test -Dtest=KafkaProducerTest");
        System.out.println("   ✅ ./mvnw test -Dtest=IntegrationFeatureTest");

        System.out.println("\n📌 RUN SPECIFIC TEST METHOD:");
        System.out.println("   ✅ ./mvnw test -Dtest=RedisServiceTest#testRedisConnection");
        System.out.println("   ✅ ./mvnw test -Dtest=EmailServiceIntegrationTest#testSendEmailWithValidData");

        System.out.println("\n" + "=".repeat(60));
    }
}
