package com.example.journalapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.journalapp.entity.User;
import com.example.journalapp.service.UserDetailServiceImpl;
import com.example.journalapp.service.UserService;
import com.example.journalapp.utilis.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/public")
@Tag(name = "Public Controller", description = "APIs for public access, including health checks, user sign-up, and login.")
public class PublicController {
    @Autowired
    UserDetailServiceImpl userDetailServiceImpl;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;
    @GetMapping("/health-check")
    @Operation(summary = "Check the health of the application")
    @ApiResponse(responseCode = "200", description = "Application is running successfully")
    public String healthCheck(){
        return "ok";
    }

    @PostMapping("/sign-up")
    @Operation(summary = "Register a new user account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "409", description = "Username already exists"),
            @ApiResponse(responseCode = "500", description = "Internal server error while creating user")
    })
    public ResponseEntity<?> addNewUser(@RequestBody User newUser){
        if (userService.findByUsername(newUser.getUserName()) != null) {
            return new ResponseEntity<>("Username already exists.", HttpStatus.CONFLICT);
        }
        try {
            userService.saveNewUser(newUser);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create user due to an internal error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/login")
    @Operation(summary = "Authenticate a user and receive a JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful, JWT returned"),
            @ApiResponse(responseCode = "401", description = "Invalid username or password")
    })
    public ResponseEntity<?> login(@RequestBody User user){
        try {
             authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));

             UserDetails userDetails = userDetailServiceImpl.loadUserByUsername(user.getUserName());
             String jwt = jwtUtil.generateToken(userDetails.getUsername());
             return new ResponseEntity<>(jwt, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }
    }

}
