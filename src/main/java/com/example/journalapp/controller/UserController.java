package com.example.journalapp.controller;

import com.example.journalapp.entity.User;
import com.example.journalapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/user")
@Tag(name = "User Controller", description = "APIs for user profile management, such as updating and deleting the user account.")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PutMapping()
    @Operation(summary = "Update the authenticated user's profile")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "User updated successfully"), @ApiResponse(responseCode = "404", description = "User not found") })
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User userInDB = userService.findByUsername(userName);
        if (userInDB != null) {
            //userInDB.setUserName(user.getUserName());
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                userInDB.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            userService.saveUser(userInDB);
            return new ResponseEntity<>(userInDB, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping()
    @Operation(summary = "Delete the authenticated user's account")
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "User deleted successfully"), @ApiResponse(responseCode = "404", description = "User not found") })
    public ResponseEntity<?> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean deleted = userService.deleteByUsername(userName);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
