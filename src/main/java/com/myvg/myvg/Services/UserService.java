package com.myvg.myvg.Services;

import com.myvg.myvg.DAO.UserDAO;
import com.myvg.myvg.DTO.UserDTO;
import com.myvg.myvg.Models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    private final MongoTemplate mongoTemplate;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserService(MongoTemplate mT) {
        this.mongoTemplate = mT;
    }

    public void registerUser(String username, String password, String email, int avatarID) {
        
        if (userDAO.findUserByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }


        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(username, encodedPassword, email, avatarID);
        mongoTemplate.save(user);
    }

    public boolean loginUser(String username, String password) {
        
        Optional<User> user = userDAO.findUserByUsername(username);
        return user.isPresent() && user.get().getPassword().equals(password);
    }

     public UserDTO getUserById(String id) {
        Optional<User> user = userDAO.findById(id);
        if (user.isPresent()) {
            User foundUser = user.get();
            return new UserDTO(foundUser.getId(), foundUser.getUsername(), foundUser.getEmail(), 0);
        }
        return null;
    }
    
}