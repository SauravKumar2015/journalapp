package com.saurav.journalApp.sheduler;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserShedulesTest {

    @Autowired
    private UserSheduler userSheduler;

    @Test
    public void testFetchUserAndSendMail() {
        userSheduler.fetchUsersAndSendEmail();
    }
}
