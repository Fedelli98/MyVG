package com.myvg.myvg.Services;

import com.myvg.myvg.DAO.UserDAO;
import com.myvg.myvg.DTO.UserDTO;
import com.myvg.myvg.Models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void registerUser(String username, String password, String email, int avatarID) throws IllegalArgumentException {
        
        if (userDAO.findUserByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        
        String encodedPassword = passwordEncoder.encode(password);
        userDAO.CreateUser(username, encodedPassword, email, avatarID);
    }

    public boolean loginUser(String username, String password) {
        
        Optional<User> user = userDAO.findUserByUsername(username);
        String encodedPassword = user.get().getPassword();
        return user.isPresent() && passwordEncoder.matches(password, encodedPassword);
    }

    public UserDTO getUserById(String id) {
        Optional<User> user = userDAO.findById(id);
        if (user.isPresent()) {
            User foundUser = user.get();
            return new UserDTO(foundUser.getId(), foundUser.getUsername(), foundUser.getEmail(), foundUser.getAvatarId());
        }
        return null;
    }

    public UserDTO getUserByUsername(String username) {
        Optional<User> user = userDAO.findUserByUsername(username);
        if (user.isPresent()) {
            User foundUser = user.get();
            return new UserDTO(foundUser.getId(), foundUser.getUsername(), foundUser.getEmail(), foundUser.getAvatarId());
        }
        return null;
    }

    public UserDTO updateAvatarID(String username, int avatarID) {
        userDAO.updateAvatarID(username, avatarID);
        return getUserByUsername(username);
    }
    
}