package com.myvg.myvg.DTO;

import com.myvg.myvg.EntityModel.UserEntity;

public class UserDTO {
    private String id;
    private String username;
    private String email;
    private int avatarID;


    public UserDTO(String username, String email, int avatarID) {
        this.username = username;
        this.email = email;
        this.avatarID = avatarID;
    }

    public UserDTO(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.username = userEntity.getUsername();
        this.email = userEntity.getEmail();
        this.avatarID = userEntity.getAvatarId();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getAvatarID() { return avatarID; }
    public void setAvatarID(int avatarID) { this.avatarID = avatarID; }

}
