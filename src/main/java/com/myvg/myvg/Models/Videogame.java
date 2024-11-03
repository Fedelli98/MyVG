package com.myvg.myvg.Models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "videogames")
public class Videogame {
    
    @Id
    private String id;
    @Field(name = "title")
    private String title;
    @Field(name = "genre")
    private String genre;
    @Field(name = "releaseYear")
    private int releaseYear;
    @Field(name = "platform")
    private List<String> platform;
    @DBRef
    @Field(name = "reviews")
    private List<Review> reviews;

    public Videogame(String title, String genre, int releaseYear, List<String> platform, List<Review> reviews) {
        super();
        this.title = title;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.platform = platform;
        this.reviews = reviews;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public int getReleaseYear() { return releaseYear; }
    public void setReleaseYear(int ry) { this.releaseYear = ry;}

    public List<String> getPlatform() { return platform; }
    public void setPlatform(List<String> platform) { this.platform = platform; }

    public List<Review> getReviews() { return reviews; }
    public void setReviews(List<Review> reviews) { this.reviews = reviews; }
}
