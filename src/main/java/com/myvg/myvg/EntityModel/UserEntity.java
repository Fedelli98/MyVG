package com.myvg.myvg.EntityModel;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.myvg.myvg.DTO.UserDTO;

@Document(collection = "users")
public class UserEntity {
    @Id
    private String id;
    @Field(name = "username")
    private String username;
    @Field(name = "password")
    private String password;
    @Field(name = "email")
    private String email;
    @Field(name = "avatarId")
    private int avatarID;
    @Field(name = "wishlist")
    private List<VideogameEntity> wishlist = new ArrayList<>();
    @Field(name = "reviews")
    private List<ReviewEntity> reviews = new ArrayList<>();

    public UserEntity() {
        this.wishlist = new ArrayList<>();
    };

    public UserEntity(String username, String password, String email, int avatarID) {
        super();
        this.username = username;
        this.password = password;
        this.email = email;
        this.avatarID = avatarID;
        this.wishlist = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }

    public UserEntity(UserDTO userDTO) {
        super();
        this.username = userDTO.getUsername();
        this.email = userDTO.getEmail();
        this.avatarID = userDTO.getAvatarID();
        this.wishlist = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }

    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getAvatarId() { return avatarID; }
    public void setAvatarId(int id) { this.avatarID = id; }

    public List<VideogameEntity> getWishlist() { return wishlist; }
    public void setWishlist(List<VideogameEntity> wishlist) { this.wishlist = wishlist; }

    public List<ReviewEntity> getReviews() { return reviews; }
    public void setReviews(List<ReviewEntity> reviews) { this.reviews = reviews; }
}


    
