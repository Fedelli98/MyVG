package com.myvg.myvg.DTO;

import com.myvg.myvg.EntityModel.UserEntity;    
import java.util.List;
import java.util.stream.Collectors;
public class UserDTO {
    private String id;
    private String username;
    private String email;
    private int avatarID;
    private List<VideogameDTO> wishlist;


    public UserDTO(String username, String email, int avatarID, List<VideogameDTO> wishlist) {
        this.username = username;
        this.email = email;
        this.avatarID = avatarID;
        this.wishlist = wishlist;
    }

    public UserDTO(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.username = userEntity.getUsername();
        this.email = userEntity.getEmail();
        this.avatarID = userEntity.getAvatarId();
        this.wishlist = userEntity.getWishlist().stream()
            .map(VideogameDTO::new)
            .collect(Collectors.toList());
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getAvatarID() { return avatarID; }
    public void setAvatarID(int avatarID) { this.avatarID = avatarID; }

    public List<VideogameDTO> getWishlist() { return wishlist; }
    public void setWishlist(List<VideogameDTO> wishlist) { this.wishlist = wishlist; }
}
