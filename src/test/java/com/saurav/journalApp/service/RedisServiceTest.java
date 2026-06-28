package com.saurav.journalApp.service;

import com.saurav.journalApp.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RedisServiceTest {

    @Autowired
    private RedisService redisService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void testRedisConnection() {
        try {
            redisTemplate.getConnectionFactory().getConnection().ping();
            System.out.println("✅ Redis connection successful - PONG received");
        } catch (Exception e) {
            System.out.println("❌ Redis connection failed: " + e.getMessage());
            fail("Redis connection failed");
        }
    }

    @Test
    public void testSetAndGetString() {
        String key = "test_string_key";
        String value = "test_value_123";
        Long ttl = 300L; // 5 minutes

        try {
            // Set value in Redis
            redisService.set(key, value, ttl);
            System.out.println("✅ Set string in Redis: " + key + " = " + value);

            // Get value from Redis
            String retrieved = redisService.get(key, String.class);
            assertEquals(value, retrieved, "Retrieved value should match set value");
            System.out.println("✅ Retrieved string from Redis: " + retrieved);

            // Clean up
            redisTemplate.delete(key);
        } catch (Exception e) {
            System.out.println("❌ Set/Get string test failed: " + e.getMessage());
            fail("Set/Get string test failed");
        }
    }

    @Test
    public void testSetAndGetUser() {
        String key = "test_user_key";
        User user = new User();
        user.setId(1L);
        user.setUserName("testuser");
        user.setPassword("password123");
        user.setEmail("test@example.com");
        user.setRoles(new ArrayList<>(List.of("USER")));
        Long ttl = 600L;

        try {
            // Set user object in Redis
            redisService.set(key, user, ttl);
            System.out.println("✅ Set User object in Redis: " + key);

            // Get user object from Redis
            User retrieved = redisService.get(key, User.class);
            assertNotNull(retrieved, "Retrieved user should not be null");
            assertEquals(user.getUserName(), retrieved.getUserName(), "Username should match");
            System.out.println("✅ Retrieved User from Redis: " + retrieved.getUserName());

            // Clean up
            redisTemplate.delete(key);
        } catch (Exception e) {
            System.out.println("❌ Set/Get User test failed: " + e.getMessage());
            fail("Set/Get User test failed");
        }
    }

    @Test
    public void testRedisExpiration() {
        String key = "expiring_key";
        String value = "will_expire";
        Long ttl = 2L; // 2 seconds

        try {
            // Set value with TTL
            redisService.set(key, value, ttl);
            System.out.println("✅ Set expiring key with TTL: " + ttl + " seconds");

            // Retrieve immediately (should exist)
            String retrieved1 = redisService.get(key, String.class);
            assertNotNull(retrieved1, "Value should exist immediately after set");
            System.out.println("✅ Value exists immediately: " + retrieved1);

            // Wait for expiration
            Thread.sleep(3000);

            // Try to retrieve after expiration (should be null)
            String retrieved2 = redisService.get(key, String.class);
            assertNull(retrieved2, "Value should be null after TTL expiration");
            System.out.println("✅ Value expired correctly after TTL");

        } catch (Exception e) {
            System.out.println("❌ Expiration test failed: " + e.getMessage());
            fail("Expiration test failed");
        }
    }

    @Test
    public void testMultipleKeysStorage() {
        try {
            // Store multiple values
            for (int i = 0; i < 5; i++) {
                String key = "multi_key_" + i;
                String value = "value_" + i;
                redisService.set(key, value, 300L);
            }
            System.out.println("✅ Stored 5 keys in Redis");

            // Retrieve and verify all values
            for (int i = 0; i < 5; i++) {
                String key = "multi_key_" + i;
                String expected = "value_" + i;
                String retrieved = redisService.get(key, String.class);
                assertEquals(expected, retrieved, "Value at key " + key + " should match");
            }
            System.out.println("✅ Retrieved and verified all 5 keys");

            // Clean up
            for (int i = 0; i < 5; i++) {
                redisTemplate.delete("multi_key_" + i);
            }
        } catch (Exception e) {
            System.out.println("❌ Multiple keys storage test failed: " + e.getMessage());
            fail("Multiple keys storage test failed");
        }
    }

    @Test
    public void testRedisNullHandling() {
        String nonExistentKey = "non_existent_key_xyz";
        try {
            Object retrieved = redisService.get(nonExistentKey, String.class);
            assertNull(retrieved, "Non-existent key should return null");
            System.out.println("✅ Null handling works correctly for non-existent key");
        } catch (Exception e) {
            System.out.println("❌ Null handling test failed: " + e.getMessage());
            fail("Null handling test failed");
        }
    }
}
