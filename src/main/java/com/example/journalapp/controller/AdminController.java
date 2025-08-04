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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/admin")
@Tag(name = "Admin Controller", description = "APIs for administrative tasks, such as managing users and roles. Requires ADMIN role.")
public class AdminController {
    @Autowired
    UserService userService;
    @GetMapping("/all-users")
    @Operation(summary = "Get all usernames in the system")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all usernames")
    public ResponseEntity<?> getAllUsers(){
        List<String> all = userService.getAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }
    @PostMapping("/create-admin")
    @Operation(summary = "Create a new user with ADMIN role")
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Admin user created successfully"), @ApiResponse(responseCode = "409", description = "Username already exists") })
    public ResponseEntity<?> createAdmin(@RequestBody User user){
        if (userService.findByUsername(user.getUserName()) != null) {
            return new ResponseEntity<>("Username already exists.", HttpStatus.CONFLICT);
        }
        userService.saveAdmin(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    @PutMapping("/make-admin/{userName}")
    @Operation(summary = "Grant ADMIN role to an existing user")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "ADMIN role granted successfully"), @ApiResponse(responseCode = "404", description = "User not found") })
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
    @Operation(summary = "Revoke ADMIN role from a user")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "ADMIN role revoked successfully"), @ApiResponse(responseCode = "404", description = "User not found") })
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