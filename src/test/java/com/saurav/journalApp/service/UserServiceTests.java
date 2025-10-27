package com.saurav.journalApp.service;

import com.saurav.journalApp.entity.User;
import com.saurav.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

//    @Disabled
    @Test
    private void testFindByUsername() {             // Tested
        User user = userRepository.findByUserName("saurav");
        assertTrue(!user.getJournalEntries().isEmpty());
    }

//    @Disabled
    @ParameterizedTest
    @CsvSource({
            "5,1, 6",
            "2,2,4",
            "1,1, 2"
    })

    private void test(int a, int b, int expected) {    // Tested
        assertEquals(expected, a + b);
    }

//    @Disabled
    @ParameterizedTest
    @ValueSource(strings = {
            "saurav",
            "Hemant"
    })
    private void  nameTest(String name) {      // Tested
        assertNotNull(userRepository.findByUserName(name));
    }


//    @ParameterizedTest
//    @ArgumentsSource(UserArgumentsProvider.class)
//    private void testSaveNewUser(User user) {
//       assertTrue(userService.saveNewUser(user));
//    }
}
