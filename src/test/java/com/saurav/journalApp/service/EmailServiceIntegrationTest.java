package com.saurav.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EmailServiceIntegrationTest {

    @Autowired
    private EmailService emailService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Test
    public void testEmailServiceBean() {
        assertNotNull(emailService, "EmailService bean should be autowired");
        System.out.println("✅ EmailService bean successfully injected");
    }

    @Test
    public void testJavaMailSenderBean() {
        assertNotNull(javaMailSender, "JavaMailSender bean should be autowired");
        System.out.println("✅ JavaMailSender bean successfully injected");
    }

    @Test
    public void testSendEmailWithValidData() {
        String to = "sauravmehta973532@gmail.com";
        String subject = "Test Email - JUnit Test";
        String body = "This is a test email from JUnit test case.\n\n" +
                      "Timestamp: " + System.currentTimeMillis() + "\n" +
                      "Status: Email sending in progress...";

        try {
            emailService.sendEmail(to, subject, body);
            System.out.println("✅ Email sent successfully to: " + to);
            System.out.println("   Subject: " + subject);
            System.out.println("   Body preview: " + body.substring(0, Math.min(50, body.length())) + "...");
        } catch (Exception e) {
            System.out.println("❌ Email sending failed: " + e.getMessage());
            e.printStackTrace();
            fail("Email sending failed: " + e.getMessage());
        }
    }

    @Test
    public void testSendMultipleEmails() {
        String[] recipients = {
            "sauravmehta973532@gmail.com",
            "test1@example.com",
            "test2@example.com"
        };

        try {
            for (String recipient : recipients) {
                String subject = "Batch Email Test - " + System.currentTimeMillis();
                String body = "This is batch email #" + (recipients.length) + " to: " + recipient;
                
                emailService.sendEmail(recipient, subject, body);
                System.out.println("✅ Email sent to: " + recipient);
            }
            System.out.println("✅ All " + recipients.length + " emails sent successfully");
        } catch (Exception e) {
            System.out.println("❌ Batch email sending failed: " + e.getMessage());
            e.printStackTrace();
            fail("Batch email sending failed");
        }
    }

    @Test
    public void testSendEmailWithWeatherReport() {
        String to = "sauravmehta973532@gmail.com";
        String subject = "Weekly Weather Report";
        String body = "Weekly Weather Summary\n\n" +
                      "Location: Ahmedabad\n" +
                      "Temperature: 35°C\n" +
                      "Feels Like: 37°C\n" +
                      "Humidity: 65%\n" +
                      "Condition: Sunny\n\n" +
                      "Have a great week!";

        try {
            emailService.sendEmail(to, subject, body);
            System.out.println("✅ Weather report email sent successfully");
        } catch (Exception e) {
            System.out.println("❌ Weather report email failed: " + e.getMessage());
            fail("Weather report email failed");
        }
    }

    @Test
    public void testSendEmailWithSentimentAnalysis() {
        String to = "sauravmehta973532@gmail.com";
        String subject = "Your Weekly Sentiment Analysis";
        String body = "Sentiment Analysis Report\n\n" +
                      "Week Of: " + System.currentTimeMillis() + "\n\n" +
                      "Overall Sentiment: POSITIVE\n" +
                      "Positive Entries: 15\n" +
                      "Negative Entries: 3\n" +
                      "Neutral Entries: 2\n\n" +
                      "Analysis: Your journal entries show predominantly positive sentiment this week.\n" +
                      "Keep up the good mood!";

        try {
            emailService.sendEmail(to, subject, body);
            System.out.println("✅ Sentiment analysis email sent successfully");
        } catch (Exception e) {
            System.out.println("❌ Sentiment analysis email failed: " + e.getMessage());
            fail("Sentiment analysis email failed");
        }
    }

    @Test
    public void testEmailConfigurationLoaded() {
        try {
            String username = ((org.springframework.mail.javamail.JavaMailSenderImpl) javaMailSender).getUsername();
            String host = ((org.springframework.mail.javamail.JavaMailSenderImpl) javaMailSender).getHost();
            int port = ((org.springframework.mail.javamail.JavaMailSenderImpl) javaMailSender).getPort();
            
            assertNotNull(username, "Email username should be configured");
            assertNotNull(host, "Email host should be configured");
            assertTrue(port > 0, "Email port should be configured");
            
            System.out.println("✅ Email configuration loaded successfully");
            System.out.println("   Username: " + username);
            System.out.println("   Host: " + host);
            System.out.println("   Port: " + port);
        } catch (Exception e) {
            System.out.println("❌ Email configuration test failed: " + e.getMessage());
            fail("Email configuration test failed");
        }
    }
}
