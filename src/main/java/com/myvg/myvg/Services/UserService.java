package com.myvg.myvg.Services;

import com.myvg.myvg.DAO.UserDAO;
import com.myvg.myvg.DTO.UserDTO;
import com.myvg.myvg.DTO.VideogameDTO;
import com.myvg.myvg.EntityModel.UserEntity;
import com.myvg.myvg.Mapper.MapperProfile;
import com.myvg.myvg.Mapper.MapperProfileFactory;
import com.myvg.myvg.Mapper.MapperProfileFactory.MapperProfileEnum;

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

    private final MapperProfile mapperUser = MapperProfileFactory.createMapperProfile(MapperProfileEnum.USER);

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
        Optional<UserEntity> res = userDAO.findById(id);
        if (res.isPresent()) {
            return mapperUser.map(res.get(), new UserDTO());
        }
        return null;
    }

    public UserDTO getUserByUsername(String username) {
        Optional<UserEntity> res = userDAO.findUserByUsername(username);
        if (res.isPresent()) {
            return mapperUser.map(res.get(), new UserDTO());
        }
        return null;
    }

    public void removeUser(String username, String password) {
        if (loginUser(username, password)) {
            userDAO.findUserByUsername(username).ifPresent(user -> userDAO.delete(user.getId()));
        }
    }

    public UserDTO updateAvatarID(String username, int avatarID) {
        userDAO.updateAvatarID(username, avatarID);
        return getUserByUsername(username);
    }

    public List<VideogameDTO> getWishlist(String userId) {
        return mapperUser.map(userDAO.findById(userId), new UserDTO()).getWishlist();
    }

    public void removeFromWishlist(String userId, VideogameDTO videogame) {
        userDAO.findById(userId).ifPresent(user -> {
            user.getWishlist().removeIf(vg -> vg.getTitle().equals(videogame.getTitle()));
            userDAO.update(user);
        });
    }
}