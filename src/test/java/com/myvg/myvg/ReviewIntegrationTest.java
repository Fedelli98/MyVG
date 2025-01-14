package com.myvg.myvg;

import com.myvg.myvg.DTO.ReviewDTO;
import com.myvg.myvg.EntityModel.ReviewEntity;
import com.myvg.myvg.EntityModel.UserEntity;
import com.myvg.myvg.EntityModel.VideogameEntity;
import com.myvg.myvg.Services.AppContext;
import com.myvg.myvg.Services.ReviewService;
import com.myvg.myvg.Services.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ReviewIntegrationTest {

    private UserEntity testUser;
    private VideogameEntity testVideogame;

    @Autowired
    private ReviewService reviewService;

    @BeforeEach
    void setUp() 
    {

    }

    @Test
    void testPostReview() {
        // Arrange
        ReviewDTO invalidRating = new ReviewDTO("testUser", "testVideogame", -1, "Great game!", 10);
        ReviewDTO invalidComment = new ReviewDTO("testUser", "testVideogame", 8, "", 10);
        ReviewDTO validReview = new ReviewDTO("testUser", "testVideogame", 8, "Great game!", 10);


        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> 
        {
            reviewService.postReview(invalidRating);
        });

        assertThrows(IllegalArgumentException.class, () -> 
        {
            reviewService.postReview(invalidComment);
        });

        var postedReview = reviewService.postReview(validReview);

        assertEquals(postedReview, true);

        //reviewService.delete
    }

    // @Test
    // void testUserCannotReviewSameGameTwice() {
    //     // Arrange
    //     ReviewDTO firstReview = new ReviewDTO();
    //     firstReview.setRating(8);
    //     firstReview.setComment("First review");
    //     firstReview.setUserId(testUser.getId());
    //     firstReview.setVideogameId(testVideogame.getId());

    //     ReviewDTO secondReview = new ReviewDTO();
    //     secondReview.setRating(7);
    //     secondReview.setComment("Second review");
    //     secondReview.setUserId(testUser.getId());
    //     secondReview.setVideogameId(testVideogame.getId());

    //     // Act & Assert
    //     reviewService.postReview(firstReview);
        
    //     assertThrows(IllegalStateException.class, () -> {
    //         reviewService.postReview(secondReview);
    //     });
    // }

}