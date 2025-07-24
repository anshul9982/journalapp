package com.example.journalapp.service;

import com.example.journalapp.entity.User;
import com.example.journalapp.repository.JournalEntryRepository;
import com.example.journalapp.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void saveNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        userRepository.save(user);
    }

    // This method is for general-purpose saving of a user object, e.g., for updates.
    public void saveUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User findByUsername(String userName){
        return userRepository.findByUserName(userName);
    }

    public void deleteById(ObjectId id){userRepository.deleteById(id);}

    
    public boolean deleteByUsername(String username) {
        User user = userRepository.findByUserName(username);
        if (user != null) {
            journalEntryRepository.deleteAll(user.getJournalEntries());
            userRepository.delete(user);
            return true;
        }
        return false;
    }

}
