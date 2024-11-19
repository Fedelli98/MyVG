package com.myvg.myvg.EntityModel;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.myvg.myvg.DTO.ReviewDTO;



@Document(collection = "reviews")
public class ReviewEntity {
    @Id
    private String id;
    @Field(name = "username")
    private String username;  
    @Field(name = "videogameTitle")
    private String videogameTitle;
    @Field(name = "rating")
    private int rating;
    @Field(name = "comment")
    private String comment;

    public ReviewEntity() {}

    public ReviewEntity(String username, String videogameTitle, int rating, String comment) {
        super();
        this.username = username;
        this.videogameTitle = videogameTitle;
        this.rating = rating;
        this.comment = comment;
    }

    public ReviewEntity(ReviewEntity review) {
        this.username = review.getUsername();
        this.videogameTitle = review.getVideogameTitle();
        this.rating = review.getRating();
        this.comment = review.getComment();
    }

    public ReviewEntity(ReviewDTO reviewDTO) {
        this.username = reviewDTO.getUserDTO().getUsername();
        this.videogameTitle = reviewDTO.getVideogameDTO().getTitle();
        this.rating = reviewDTO.getRating();
        this.comment = reviewDTO.getComment();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getVideogameTitle() { return videogameTitle; }
    public void setVideogameTitle(String videogameTitle) { this.videogameTitle = videogameTitle; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
    
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }


}
