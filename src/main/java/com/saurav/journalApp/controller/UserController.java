package com.saurav.journalApp.controller;

import com.saurav.journalApp.entity.User;
import com.saurav.journalApp.repository.UserRepository;
import com.saurav.journalApp.service.UserService;
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

    @Autowired
    private UserRepository userSRepository;
    @Autowired
    private UserRepository userRepository;

//    Not imp
//    @GetMapping("/{userName}")
//    public ResponseEntity<User> getUser(@PathVariable String userName) {
//        User user = userService.findByUserName(userName);
//        if (user != null) {
//            return new ResponseEntity<>(user.get(), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
    //

    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody User user) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb =  userService.findByUserName(userName);
        userInDb.setUserName(user.getUserName());
        userInDb.setPassword(user.getPassword());
        userService.saveNewUser(userInDb);

        return new ResponseEntity<>(userInDb, HttpStatus.NO_CONTENT);
    }


//
//    @DeleteMapping()
//    public ResponseEntity<?> deleteUserById() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//
//        User user = userRepository.findByUserName(username);
//        if (user == null) {
//            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
//        }
//
//        userRepository.delete(user);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }


    @DeleteMapping
    public ResponseEntity<?> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


//
//    @GetMapping("/test")
//    public ResponseEntity<String> testEndpoint() {
//        return ResponseEntity.ok("UserController is active!");
//    }


}
