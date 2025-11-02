package com.saurav.journalApp.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;


    @Test
    void testSendEmail() {
        emailService.sendEmail("sauravmehta973532@gmail.com",
                "Testing java mail sender",
                "hii, How are you");
    }
}
