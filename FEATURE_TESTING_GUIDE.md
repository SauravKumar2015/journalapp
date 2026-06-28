# Redis, Kafka & Email Feature Testing Guide

## Overview
This guide explains how to test the three major external services integrated in the JournalApp:
- **Redis** - Caching layer
- **Kafka** - Message queue (disabled in dev profile)
- **Email** - SMTP notifications

---

## 📌 Test Files Created

### 1. RedisServiceTest.java
**Location:** `src/test/java/com/saurav/journalApp/service/RedisServiceTest.java`

**Purpose:** Verify Redis connectivity and operations

**Test Cases:**
- `testRedisConnection()` - Checks Redis server availability
- `testSetAndGetString()` - String value persistence
- `testSetAndGetUser()` - Object serialization/deserialization
- `testRedisExpiration()` - TTL and key expiration
- `testMultipleKeysStorage()` - Bulk operations
- `testRedisNullHandling()` - Null value handling

**What it validates:**
- ✅ Redis connection is working
- ✅ Data is stored correctly
- ✅ TTL (Time-To-Live) works as expected
- ✅ Objects serialize/deserialize properly
- ✅ Multiple keys can be managed

**Expected Output:**
```
✅ Redis connection successful - PONG received
✅ Set string in Redis: test_string_key = test_value_123
✅ Retrieved string from Redis: test_value_123
✅ Value expires correctly after TTL
```

---

### 2. EmailServiceIntegrationTest.java
**Location:** `src/test/java/com/saurav/journalApp/service/EmailServiceIntegrationTest.java`

**Purpose:** Verify email sending functionality

**Test Cases:**
- `testEmailServiceBean()` - Checks bean injection
- `testJavaMailSenderBean()` - Checks JavaMailSender configuration
- `testSendEmailWithValidData()` - Send single email
- `testSendMultipleEmails()` - Batch email sending
- `testSendEmailWithWeatherReport()` - Email with formatted content
- `testSendEmailWithSentimentAnalysis()` - Complex email template
- `testEmailConfigurationLoaded()` - Verify SMTP config

**What it validates:**
- ✅ Email service is properly configured
- ✅ SMTP connection parameters are correct
- ✅ Emails can be sent successfully
- ✅ Multiple recipients can receive emails
- ✅ Email content is formatted correctly

**Expected Output:**
```
✅ EmailService bean successfully injected
✅ Email sent successfully to: sauravmehta973532@gmail.com
   Subject: Test Email - JUnit Test
✅ All 3 emails sent successfully
✅ Email configuration loaded successfully
   Username: sauravkumar973532@gmail.com
   Host: smtp.gmail.com
   Port: 587
```

---

### 3. KafkaProducerTest.java
**Location:** `src/test/java/com/saurav/journalApp/service/KafkaProducerTest.java`

**Purpose:** Verify Kafka producer functionality

**Test Cases:**
- `testKafkaTemplateBean()` - Checks KafkaTemplate availability
- `testSendSentimentMessage()` - Send single sentiment message
- `testSendMultipleSentimentMessages()` - Batch sentiment messages
- `testKafkaConfiguration()` - Verify Kafka config
- `testSentimentDataObject()` - Validate data model

**What it validates:**
- ✅ KafkaTemplate is properly configured
- ✅ Messages can be sent to Kafka topics
- ✅ SentimentData objects are serialized correctly
- ✅ Consumer can receive and process messages

**Important Note:** ⚠️ **Kafka is DISABLED in dev profile**
- `kafka.listener.auto-startup: false` prevents startup errors
- To enable Kafka testing, switch to production profile or enable in config

**Expected Output:**
```
⚠️  Kafka is disabled in dev profile (auto-startup: false)
   To enable Kafka testing, use production profile or enable in dev config

OR (if enabled):

✅ KafkaTemplate bean successfully injected
✅ Sentiment message sent to Kafka topic: weekly-sentiment
   User: testuser
   Sentiment: Your journal this week shows POSITIVE sentiment!
```

---

### 4. IntegrationFeatureTest.java
**Location:** `src/test/java/com/saurav/journalApp/service/IntegrationFeatureTest.java`

**Purpose:** Overall feature availability check and testing guide

**Test Cases:**
- `testAllFeaturesBeanAvailability()` - Check all services
- `testRedisFeature()` - Redis functionality
- `testEmailFeature()` - Email functionality
- `testKafkaFeature()` - Kafka availability
- `printTestingGuide()` - Interactive testing guide

---

## 🚀 How to Run the Tests

### 1️⃣ Run All Tests
```bash
cd e:\JAVA_FILES\journalApp
.\mvnw.cmd clean test
```

**Output:** Runs all test classes in the project

---

### 2️⃣ Run Specific Test File

#### Run Redis Tests
```bash
.\mvnw.cmd test -Dtest=RedisServiceTest
```

#### Run Email Tests
```bash
.\mvnw.cmd test -Dtest=EmailServiceIntegrationTest
```

#### Run Kafka Tests
```bash
.\mvnw.cmd test -Dtest=KafkaProducerTest
```

#### Run Integration Tests
```bash
.\mvnw.cmd test -Dtest=IntegrationFeatureTest
```

---

### 3️⃣ Run Specific Test Method

#### Test Redis Connection
```bash
.\mvnw.cmd test -Dtest=RedisServiceTest#testRedisConnection
```

#### Test Single Email
```bash
.\mvnw.cmd test -Dtest=EmailServiceIntegrationTest#testSendEmailWithValidData
```

#### Test Kafka Message
```bash
.\mvnw.cmd test -Dtest=KafkaProducerTest#testSendSentimentMessage
```

---

### 4️⃣ Run with Specific Profile (For Kafka)

#### Enable Kafka (with production profile)
```bash
.\mvnw.cmd clean test -Dspring.profiles.active=prod
```

#### Run with test output
```bash
.\mvnw.cmd clean test -X
```

---

## 📊 Test Output Analysis

### ✅ Green Checkmark (✅)
- **Meaning:** Test PASSED
- **Action:** Feature is working correctly

### ⚠️ Warning (⚠️)
- **Meaning:** Feature is DISABLED or requires configuration
- **Action:** Check configuration or enable feature

### ❌ Red X (❌)
- **Meaning:** Test FAILED
- **Action:** Feature has an issue, check error message

---

## 🔧 Configuration Details

### Redis Configuration (Dev Profile)
```yaml
spring:
  redis:
    host: redis-12224.crce182.ap-south-1-1.ec2.redns.redis-cloud.com
    port: 12224
    password: LGEuzUjhPPJaDdWPNoNOLYC1zwsXdmwg
    ssl: true
```

### Email Configuration (Dev Profile)
```yaml
spring:
  mail:
    host: smtp.gmail.com
    port: 587
    username: sauravkumar973532@gmail.com
    password: "wyrg ytop jryn jwfe"
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
```

### Kafka Configuration (Dev Profile)
```yaml
kafka:
  bootstrap-servers: pkc-9q8rv.ap-south-2.aws.confluent.cloud:9092
  listener:
    auto-startup: false  # Disabled to prevent auth errors
```

---

## 🎯 What Each Test Verifies

| Feature | Test Class | What It Checks | Status |
|---------|-----------|-----------------|--------|
| **Redis** | RedisServiceTest | Connection, get/set, TTL, serialization | ✅ Active |
| **Email** | EmailServiceIntegrationTest | SMTP config, send email, batch send | ✅ Active |
| **Kafka** | KafkaProducerTest | Topic publish, message sending | ⚠️ Disabled (Dev) |

---

## 📈 Expected Results Summary

### Redis Tests
- **All 6 tests should PASS** ✅
- Redis server must be accessible
- Requires: Redis instance running on configured host

### Email Tests
- **All 7 tests should PASS** ✅
- Gmail SMTP credentials must be valid
- Requires: Gmail account with app password enabled

### Kafka Tests
- **4 tests should show SKIPPED/DISABLED** ⚠️ (in dev profile)
- 1 test should show PASSED (SentimentDataObject test)
- Requires: Enable `kafka.listener.auto-startup: true` to fully activate

---

## 🔍 Troubleshooting

### Redis Test Fails
**Problem:** "Redis connection failed"
- ✅ Solution: Check Redis instance is running
- ✅ Solution: Verify host/port/password in application-dev.yml
- ✅ Solution: Check SSL certificate if ssl=true

### Email Test Fails
**Problem:** "Authentication failed for SMTP"
- ✅ Solution: Verify Gmail app password is correct
- ✅ Solution: Enable "Less secure app access" in Gmail
- ✅ Solution: Check SMTP port (587 for TLS)

### Kafka Test Fails
**Problem:** "Kafka connection timeout"
- ✅ Solution: Enable in application-dev.yml: `kafka.listener.auto-startup: true`
- ✅ Solution: Verify Confluent Cloud credentials
- ✅ Solution: Check network connectivity to Kafka broker

---

## 📝 Example: Running Test Sequence

```bash
# Step 1: Test Redis
.\mvnw.cmd test -Dtest=RedisServiceTest

# Step 2: Test Email
.\mvnw.cmd test -Dtest=EmailServiceIntegrationTest

# Step 3: Test Kafka (optional - disabled in dev)
.\mvnw.cmd test -Dtest=KafkaProducerTest

# Step 4: View Overall Status
.\mvnw.cmd test -Dtest=IntegrationFeatureTest
```

---

## 📚 Additional Resources

- **Redis Documentation:** https://redis.io/documentation
- **Spring Data Redis:** https://spring.io/projects/spring-data-redis
- **Spring Mail:** https://spring.io/projects/spring-integration-mail
- **Apache Kafka:** https://kafka.apache.org/documentation/
- **Spring Kafka:** https://spring.io/projects/spring-kafka

---

## ✨ Next Steps

1. **Run the integration test:** `.\mvnw.cmd test -Dtest=IntegrationFeatureTest`
2. **Check which features are working:** Look for ✅ marks
3. **Fix any failures:** Follow troubleshooting guide
4. **Enable Kafka if needed:** Change config and rerun
5. **Monitor logs:** Check application output for detailed messages

---

**Last Updated:** 2026-06-24
**Test Framework:** JUnit 5 + Spring Boot Test
**Test Version:** 1.0
