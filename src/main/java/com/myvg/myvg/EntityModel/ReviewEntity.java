package com.myvg.myvg.EntityModel;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.myvg.myvg.DTO.ReviewDTO;



@Document(collection = "reviews")
public class ReviewEntity {
    @Id
    private String id;
    @Field(name = "userId")
    private String userId;  
    @Field(name = "videogameId")
    private String videogameId;
    @Field(name = "rating")
    private int rating;
    @Field(name = "comment")
    private String comment;

    public ReviewEntity() {}

    public ReviewEntity(String userId, String videogameId, int rating, String comment) {
        super();
        this.userId = userId;
        this.videogameId = videogameId;
        this.rating = rating;
        this.comment = comment;
    }

    public ReviewEntity(ReviewEntity review) {
        this.userId = review.getUserId();
        this.videogameId = review.getVideogameId();
        this.rating = review.getRating();
        this.comment = review.getComment();
    }

    public ReviewEntity(ReviewDTO reviewDTO) {
        this.userId = reviewDTO.getUserDTO().getId();
        this.videogameId = reviewDTO.getVideogameDTO().getId();
        this.rating = reviewDTO.getRating();
        this.comment = reviewDTO.getComment();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getVideogameId() { return videogameId; }
    public void setVideogameId(String videogameId) { this.videogameId = videogameId; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
    
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }


}
