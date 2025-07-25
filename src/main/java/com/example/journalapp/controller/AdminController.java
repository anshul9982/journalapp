package com.example.journalapp.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.journalapp.entity.User;
import com.example.journalapp.service.UserService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/admin")

public class AdminController {
    @Autowired
    UserService userService;
    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        List<String> all = userService.getAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }
    @PostMapping("/create-admin")
    public ResponseEntity<?> createAdmin(@RequestBody User user){
        if (userService.findByUsername(user.getUserName()) != null) {
            return new ResponseEntity<>("Username already exists.", HttpStatus.CONFLICT);
        }
        userService.saveAdmin(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    @PutMapping("/make-admin/{userName}")
    public ResponseEntity<?> makeAdmin(@PathVariable String userName ){
        User user = userService.findByUsername(userName);
        if (user != null) {
            List<String> roles = new ArrayList<>(user.getRoles());
            if (!roles.contains("ADMIN")) {
                roles.add("ADMIN");
                user.setRoles(roles);
                userService.saveUser(user);
            }
            return new ResponseEntity<>(user.getUserName(), HttpStatus.OK);
        }
        return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/remove-admin/{userName}")
    public ResponseEntity<?> removeAdmin(@PathVariable String userName){
        User user = userService.findByUsername(userName);
        if(user!=null){
            List<String> roles = new ArrayList<>(user.getRoles());
            if (!roles.isEmpty()){
                roles.remove("ADMIN");
                user.setRoles(roles);
                userService.saveUser(user);
            }
            return new ResponseEntity<>(user.getUserName() + " Admin status revoked", HttpStatus.OK);
        }
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

}