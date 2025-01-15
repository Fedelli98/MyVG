package com.myvg.myvg;

import com.myvg.myvg.DTO.ReviewDTO;
import com.myvg.myvg.Services.ReviewService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ReviewIntegrationTest {

    @Autowired
    private ReviewService reviewService;

    @Test
    void testPostReview() {
        // Arrange
        ReviewDTO invalidRating = new ReviewDTO("testUser", 
                                        "testVideogame", -1, "Great game!", 10);
        ReviewDTO invalidLikes = new ReviewDTO("testUser", 
                                        "testVideogame", 8, "", -1);
        ReviewDTO validReview = new ReviewDTO("testUser", 
                                        "The Last of Us Part I", 8, "Great game!", 10);
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            reviewService.postReview(invalidRating);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            reviewService.postReview(invalidLikes);
        });
        boolean result = reviewService.postReview(validReview);
        assertEquals(result, true);
        reviewService.delete(validReview.getId());
    }

    @Test
    void testUserCannotReviewSameGameTwice() {
        // Arrange
        ReviewDTO firstReview = new ReviewDTO("testUser", 
                        "The Last of Us Part I", 8, "Great game!", 10);
        ReviewDTO secondReview = new ReviewDTO(
                        "testUser", "The Last of Us Part I", 8, "Great game!", 10);
        // Act & Assert
        reviewService.postReview(firstReview);
        
        assertThrows(IllegalArgumentException.class, () -> {
            reviewService.postReview(secondReview);
            reviewService.delete(secondReview.getId());
        });

        reviewService.delete(firstReview.getId());
    }

}