package com.myvg.myvg.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "reviews")
public class Review {
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

    public Review(String userId, String videogameId, int rating, String comment) {
        super();
        this.userId = userId;
        this.videogameId = videogameId;
        this.rating = rating;
        this.comment = comment;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getVideogameString() { return videogameId; }
    public void setVideogameString(String videogameId) { this.videogameId = videogameId; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
    
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }


}
