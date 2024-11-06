package com.myvg.myvg.EntityModel;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.myvg.myvg.DTO.VideogameDTO;

@Document(collection = "videogames")
public class VideogameEntity {
    
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
    private List<ReviewEntity> reviews;

    public VideogameEntity() {};

    public VideogameEntity(String title, String genre, int releaseYear, List<String> platform, List<ReviewEntity> reviews) {
        super();
        this.title = title;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.platform = platform;
        this.reviews = reviews;
    }

    public VideogameEntity(VideogameDTO videogameDTO) {
        super();
        this.title = videogameDTO.getTitle();
        this.genre = videogameDTO.getGenre();
        this.releaseYear = videogameDTO.getReleaseYear();
        this.platform = videogameDTO.getPlatform();
        this.reviews = videogameDTO.getReviews();
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

    public List<ReviewEntity> getReviews() { return reviews; }
    public void setReviews(List<ReviewEntity> reviews) { this.reviews = reviews; }
}
