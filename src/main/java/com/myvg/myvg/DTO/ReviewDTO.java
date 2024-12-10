package com.myvg.myvg.DTO;


public class ReviewDTO {
    private String id;
    private UserDTO userDTO;
    private VideogameDTO videogameDTO;

    private int rating;
    private String comment;

    public ReviewDTO() {}

    public ReviewDTO(UserDTO userDTO, VideogameDTO videogameDTO, int rating, String comment) {

        this.userDTO = userDTO;
        this.videogameDTO = videogameDTO;
        this.rating = rating;
        this.comment = comment;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public UserDTO getUserDTO() { return userDTO; }
    public void setUserDTO(UserDTO userDTO) { this.userDTO = userDTO; }

    public VideogameDTO getVideogameDTO() { return videogameDTO; }
    public void setVideogameDTO(VideogameDTO videogameDTO) { this.videogameDTO = videogameDTO; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}
