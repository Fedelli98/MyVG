package com.myvg.myvg.DTO;


public class ReviewDTO {
    private String id;
    private String username;
    private String videogame;

    private int rating;
    private String comment;
    private int likes;

    public ReviewDTO() {}

    public ReviewDTO(String username, String videogame, int rating, String comment,  int likes) {

        this.username = username;
        this.videogame = videogame;
        this.rating = rating;
        this.comment = comment;
        this.likes = likes;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getVideogameTitle() { return videogame; }
    public void setVideogameTitle(String videogame) { this.videogame = videogame; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public int getLikes() { return likes; }
    public void setLikes(int likes) { this.likes = likes; }

    public void incrementLikes() { likes += 1; }

}
