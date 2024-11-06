package com.myvg.myvg.DTO;

import java.util.List;

import com.myvg.myvg.EntityModel.ReviewEntity;
import com.myvg.myvg.EntityModel.VideogameEntity;

public class VideogameDTO {
    private String id;
    private String title;
    private String genre;
    private int releaseYear;
    private List<String> platform;
    private List<ReviewEntity> reviews;

    public VideogameDTO(String title, String genre, int releaseYear, List<String> platform, List<ReviewEntity> reviews) {

        this.title = title;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.platform = platform;
        this.reviews = reviews;
    }

    public VideogameDTO(VideogameEntity videogame) {
        this.id = videogame.getId();
        this.title = videogame.getTitle();
        this.genre = videogame.getGenre();
        this.releaseYear = videogame.getReleaseYear();
        this.platform = videogame.getPlatform();
        this.reviews = videogame.getReviews();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public int getReleaseYear() { return releaseYear; }
    public void setReleaseYear(int releaseYear) { this.releaseYear = releaseYear; }

    public List<String> getPlatform() { return platform; }
    public void setPlatform(List<String> platform) { this.platform = platform; }

    public List<ReviewEntity> getReviews() { return reviews; }
    public void setReviews(List<ReviewEntity> reviews) { this.reviews = reviews; }
}
