package com.myvg.myvg.Services;

import com.myvg.myvg.DAO.UserDAO;
import com.myvg.myvg.DTO.UserDTO;
import com.myvg.myvg.DTO.VideogameDTO;
import com.myvg.myvg.EntityModel.UserEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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
        userDAO.create(new UserEntity(username, encodedPassword, email, avatarID));
    }

    public boolean loginUser(String username, String password) {
        
        Optional<UserEntity> user = userDAO.findUserByUsername(username);
        String encodedPassword = user.get().getPassword();
        return user.isPresent() && passwordEncoder.matches(password, encodedPassword);
    }

    public UserDTO getUserById(String id) {
        Optional<UserEntity> user = userDAO.findById(id);
        if (user.isPresent()) {
            return new UserDTO(user.get());
        }
        return null;
    }

    public UserDTO getUserByUsername(String username) {
        Optional<UserEntity> user = userDAO.findUserByUsername(username);
        if (user.isPresent()) {
            return new UserDTO(user.get());
        }
        return null;
    }

    public UserDTO updateAvatarID(String username, int avatarID) {
        userDAO.updateAvatarID(username, avatarID);
        return getUserByUsername(username);
    }

    public List<VideogameDTO> getWishlist(String userId) {
        return userDAO.findById(userId)
            .map(user -> user.getWishlist().stream()
                .map(VideogameDTO::new)
                .collect(Collectors.toList()))
            .orElse(new ArrayList<>());
    }
}