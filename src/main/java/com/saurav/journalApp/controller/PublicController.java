package com.saurav.journalApp.controller;

import com.saurav.journalApp.DTO.UserRequestDTO;
import com.saurav.journalApp.entity.User;
import com.saurav.journalApp.service.UserService;
import com.saurav.journalApp.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    String healthCheck() {
        return "Health is good";
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody UserRequestDTO dto) {
        // Check if username already exists before trying to save
        if (userService.findByUserName(dto.getUserName()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Username '" + dto.getUserName() + "' already exists");
        }
        try {
            User user = new User();
            user.setUserName(dto.getUserName());
            user.setPassword(dto.getPassword());
            if (dto.getEmail() != null) user.setEmail(dto.getEmail());
            if (dto.getSentimentAnalysis() != null) user.setSentimentAnalysis(dto.getSentimentAnalysis());

            userService.saveNewUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserRequestDTO dto) {  // only userName + password needed
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getUserName(), dto.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getUserName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Login failed for user: {}", dto.getUserName());
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
        }
    }

}
