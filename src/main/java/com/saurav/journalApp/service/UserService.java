package com.saurav.journalApp.service;

import com.saurav.journalApp.entity.User;
import com.saurav.journalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // Use standard interface
import org.springframework.stereotype.Service;
import com.saurav.journalApp.DTO.UserRequestDTO;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // FIX 1: Inject the global PasswordEncoder bean configured in your security config
    @Autowired
    private PasswordEncoder passwordEncoder; 

    public boolean saveNewUser(User user) {
        try {
            user.setRoles(Arrays.asList("USER"));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while saving new user: ", e); // Log the actual stack trace!
            return false;
        }
    }

    // FIX 2: Create a dedicated method for updating an existing user safely
    public User updateUser(String authenticatedUsername, UserRequestDTO dto) {
        User existingUser = userRepository.findByUserName(authenticatedUsername);
        if (existingUser == null) throw new RuntimeException("User not found: " + authenticatedUsername);

        if (dto.getUserName() != null && !dto.getUserName().isEmpty()) {
            existingUser.setUserName(dto.getUserName());
        }
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        if (dto.getEmail() != null && !dto.getEmail().isEmpty()) {
            existingUser.setEmail(dto.getEmail());
        }
        if (dto.getSentimentAnalysis() != null) {
            existingUser.setSentimentAnalysis(dto.getSentimentAnalysis());
        }

        return userRepository.save(existingUser);
    }

    public void saveAdmin(User user) {
        user.setRoles(Arrays.asList("USER", "ADMIN"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    // Use this strictly for internal saves where password encryption is handled elsewhere
    public void saveUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
}
