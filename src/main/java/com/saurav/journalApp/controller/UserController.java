package com.saurav.journalApp.controller;

import com.saurav.journalApp.DTO.UserRequestDTO;
import com.saurav.journalApp.DTO.UserResponseDTO;
import com.saurav.journalApp.api.response.WeatherResponse;
import com.saurav.journalApp.entity.User;
import com.saurav.journalApp.repository.UserRepository;
import com.saurav.journalApp.service.UserService;
import com.saurav.journalApp.service.WeatherService;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    // @Autowired
    // private UserRepository userSRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WeatherService weatherService;


    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserRequestDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedUsername = authentication.getName();

        userService.updateUser(authenticatedUsername, dto);
        return ResponseEntity.ok("User updated successfully");  // ✅ don't return user data
    }


    @Transactional
    @DeleteMapping
    public ResponseEntity<?> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // CascadeType.ALL + orphanRemoval on User handles journal entries automatically
        userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<?> greeting() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String greeting = "Hi " + authentication.getName();
        try {
            WeatherResponse weatherResponse = weatherService.getWeather("Ahmedabad");
            if (weatherResponse != null && weatherResponse.getCurrent() != null) {
                greeting += ", Weather feels like " + weatherResponse.getCurrent().getFeelslike();
            }
        } catch (Exception e) {
            // weather service error - continue without weather info
        }
        return new ResponseEntity<>(greeting, HttpStatus.OK);
    }



}
