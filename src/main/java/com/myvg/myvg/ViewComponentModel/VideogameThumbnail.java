package com.myvg.myvg.ViewComponentModel;
import com.myvg.myvg.DTO.VideogameDTO;

public class VideogameThumbnail {
    private String title;
    private String imageUrl;
    private int avgRating;

    public VideogameThumbnail() {}

    public VideogameThumbnail(String title, String imageUrl, int avgRating) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.avgRating = avgRating;
    }

    public VideogameThumbnail(VideogameDTO videogame) {
        this.title = videogame.getTitle();
        //this.imageUrl = videogame.getImage();
        this.avgRating = (int) videogame.getReviews().stream()
            .mapToInt(review -> review.getRating())
            .average()
            .orElse(0.0);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getReviewScore() {
        return avgRating;
    }

    public void setReviewScore(int avgRating) {
        this.avgRating = avgRating;
    }
}
