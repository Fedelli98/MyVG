package com.myvg.myvg.ViewComponentModel;
import com.myvg.myvg.DTO.VideogameDTO;

public class VideogameThumbnail {
    private String title;
    private String imagePath;
    private int avgRating;

    public VideogameThumbnail() {}

    public VideogameThumbnail(String title, String imageUrl, int avgRating) {
        this.title = title;
        this.imagePath = imageUrl;
        this.avgRating = avgRating;
    }

    public VideogameThumbnail(VideogameDTO videogame) {
        this.title = videogame.getTitle();
        this.imagePath = videogame.getVideogameCoverPath();
        this.avgRating = calculateAvgRating(videogame);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getReviewScore() {
        return avgRating;
    }

    public void setReviewScore(int avgRating) {
        this.avgRating = avgRating;
    }

    public int calculateAvgRating(VideogameDTO videogame) {
        if(videogame.getReviews().isEmpty() || videogame.getReviews() == null) {
            return 0;
        }
        return (int) videogame.getReviews().stream()
            .mapToInt(review -> review.getRating())
            .average()
            .orElse(0.0);
    }
}
