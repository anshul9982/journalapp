package com.example.journalapp.controller;

import com.example.journalapp.entity.User;
import com.example.journalapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> allUsers = userService.getAll();
            if (allUsers!=null && !allUsers.isEmpty()){
                return new ResponseEntity<>(allUsers, HttpStatus.OK);
            }
            else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } 
    @PostMapping    
    public ResponseEntity<User> addNewUser(@RequestBody User newUser){
        try {
            userService.saveUser(newUser);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping
    public ResponseEntity<User>findByUserName(@RequestBody String username){
        return null;
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        User userInDB = userService.findByUsername(user.getUserName());
        if (userInDB!=null) {
            userInDB.setUserName(user.getUserName()!=null && !user.getUserName().equals("")?user.getUserName():userInDB.getUserName());
            userInDB.setPassword(user.getPassword()!=null && !user.getPassword().equals("")?user.getPassword():userInDB.getPassword());
            userService.saveUser(userInDB);
            return new ResponseEntity<>(userInDB, HttpStatus.OK);
        }else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
    } 
}
