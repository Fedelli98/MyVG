package com.myvg.myvg.DTO;
import java.util.List;

public class UserDTO {
    private String id;
    private String username;
    private String email;
    private int avatarID;
    private List<VideogameDTO> wishlist;
    private List<ReviewDTO> reviews;

    public UserDTO() {}
    
    public UserDTO(String username, String email, int avatarID, List<VideogameDTO> wishlist, List<ReviewDTO> reviews) {
        this.username = username;
        this.email = email;
        this.avatarID = avatarID;
        this.wishlist = wishlist;
        this.reviews = reviews;
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

    public List<ReviewDTO> getReviews() { return reviews; }
    public void setReviews(List<ReviewDTO> reviews) { this.reviews = reviews; }
}
