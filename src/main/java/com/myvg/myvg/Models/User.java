package com.myvg.myvg.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "users")
public class User {
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

    public User() {};

    public User(String username, String password, String email, int avatarID) {
        super();
        this.username = username;
        this.password = password;
        this.email = email;
        this.avatarID = avatarID;
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
}


    
