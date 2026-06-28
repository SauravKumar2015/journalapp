package com.saurav.journalApp.controller;

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
    public ResponseEntity<UserResponseDTO> updateUser(@RequestBody User user) {
        // 2. Execute your standard update logic via repository layer
        User updatedUser = userRepository.save(user); 
        
        // 3. Map the entity fields manually into your new DTO
        UserResponseDTO responseDto = new UserResponseDTO();
        responseDto.setUserName(updatedUser.getUserName());
        
        // 4. Return the DTO wrapper with an HTTP 200 OK status
        return ResponseEntity.ok(responseDto);
    }


    @Transactional
    @DeleteMapping
    public ResponseEntity<?> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
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
