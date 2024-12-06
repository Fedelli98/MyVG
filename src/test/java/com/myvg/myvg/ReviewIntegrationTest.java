// package com.myvg.myvg;

// import com.myvg.myvg.DTO.ReviewDTO;
// import com.myvg.myvg.EntityModel.ReviewEntity;
// import com.myvg.myvg.EntityModel.UserEntity;
// import com.myvg.myvg.EntityModel.VideogameEntity;
// import com.myvg.myvg.Services.AppContext;
// import com.myvg.myvg.Services.ReviewService;
// import com.myvg.myvg.Services.UserService;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.ActiveProfiles;
// import org.springframework.transaction.annotation.Transactional;

// import java.time.LocalDateTime;

// import static org.junit.jupiter.api.Assertions.*;

// @SpringBootTest
// @ActiveProfiles("test")
// @Transactional
// public class ReviewIntegrationTest {

//     private UserEntity testUser;
//     private VideogameEntity testVideogame;

//     @Autowired
//     private ReviewService reviewService;

//     @Autowired
//     private UserService userService;

//     @Autowired
//     private AppContext appContext;


//     @BeforeEach
//     void setUp() {
//         // Crea un utente di test
//         testUser = new UserEntity();
//         testUser.setUsername("testUser");
//         testUser.setPassword("password123");
//         testUser.setEmail("test@example.com");

//         userService.registerUser(testUser.getUsername(), testUser.getPassword(), testUser.getEmail(), 1);
//         userService.loginUser(null, null);

//         //assert in app context that current user is testUser
//         assertEquals(testUser, userService.getCurrentUser());

//         // Crea un videogioco di test
//         testVideogame = new VideogameEntity();
//         testVideogame.setTitle("Test Game");
//         testVideogame.setDescription("Test Description");
//         testVideogame.setReleaseDate(LocalDateTime.now());
//         videogameRepository.save(testVideogame);
//     }

//     @Test
//     void testPostNewReview() {
//         // Arrange
//         ReviewDTO reviewDTO = new ReviewDTO();
//         reviewDTO.setRating(8);
//         reviewDTO.setComment("Great game!");
//         reviewDTO.setUserName(testUser.getId());
//         reviewDTO.setVideogameId(testVideogame.getId());

//         // Act
//         Review savedReview = reviewService.postReview(reviewDTO);

//         // Assert
//         assertNotNull(savedReview);
//         assertNotNull(savedReview.getId());
//         assertEquals(8, savedReview.getRating());
//         assertEquals("Great game!", savedReview.getComment());
//         assertEquals(testUser.getId(), savedReview.getUser().getId());
//         assertEquals(testVideogame.getId(), savedReview.getVideogame().getId());
//     }

//     @Test
//     void testUserCannotReviewSameGameTwice() {
//         // Arrange
//         ReviewDTO firstReview = new ReviewDTO();
//         firstReview.setRating(8);
//         firstReview.setComment("First review");
//         firstReview.setUserId(testUser.getId());
//         firstReview.setVideogameId(testVideogame.getId());

//         ReviewDTO secondReview = new ReviewDTO();
//         secondReview.setRating(7);
//         secondReview.setComment("Second review");
//         secondReview.setUserId(testUser.getId());
//         secondReview.setVideogameId(testVideogame.getId());

//         // Act & Assert
//         reviewService.postReview(firstReview);
        
//         assertThrows(IllegalStateException.class, () -> {
//             reviewService.postReview(secondReview);
//         });
//     }

//     @Test
//     void testReviewUpdatesGameAverageRating() {
//         // Arrange
//         ReviewDTO review1 = new ReviewDTO();
//         review1.setRating(8);
//         review1.setComment("First user review");
//         review1.setUserId(testUser.getId());
//         review1.setVideogameId(testVideogame.getId());

//         // Create another user for second review
//         User secondUser = new User();
//         secondUser.setUsername("testUser2");
//         secondUser.setPassword("password123");
//         secondUser.setEmail("test2@example.com");
//         userRepository.save(secondUser);

//         ReviewDTO review2 = new ReviewDTO();
//         review2.setRating(6);
//         review2.setComment("Second user review");
//         review2.setUserId(secondUser.getId());
//         review2.setVideogameId(testVideogame.getId());

//         // Act
//         reviewService.postReview(review1);
//         reviewService.postReview(review2);

//         // Assert
//         Videogame updatedGame = videogameRepository.findById(testVideogame.getId()).orElseThrow();
//         assertEquals(7.0, updatedGame.getAverageRating(), 0.01);
//     }

//     @Test
//     void testInvalidRatingThrowsException() {
//         // Arrange
//         ReviewDTO reviewDTO = new ReviewDTO();
//         reviewDTO.setRating(11); // Invalid rating > 10
//         reviewDTO.setComment("Invalid review");
//         reviewDTO.setUserId(testUser.getId());
//         reviewDTO.setVideogameId(testVideogame.getId());

//         // Act & Assert
//         assertThrows(IllegalArgumentException.class, () -> {
//             reviewService.postReview(reviewDTO);
//         });
//     }
// }