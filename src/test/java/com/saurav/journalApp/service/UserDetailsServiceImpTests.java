package com.saurav.journalApp.service;

import com.saurav.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.saurav.journalApp.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

@ActiveProfiles
public class UserDetailsServiceImpTests {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void SetUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void loadUserDetailsService() {
        when(userRepository.findByUserName("saurav")).thenReturn(User.builder().userName("Ayan Mansuri").password("He is my friend").roles(new ArrayList<>()).build());
        UserDetails user = userDetailsService.loadUserByUsername("saurav");
        Assertions.assertNotNull(user);
//        System.out.println(user.getUsername() +  " " + user.getPassword());
    }
}
