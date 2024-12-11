package com.myvg.myvg.Services;

import java.util.List;
import java.util.Optional;
import java.util.Comparator;

import com.myvg.myvg.DAO.ReviewDAO;
import com.myvg.myvg.DAO.UserDAO;
import com.myvg.myvg.DAO.VideogameDAO;
import com.myvg.myvg.EntityModel.ReviewEntity;
import com.myvg.myvg.EntityModel.UserEntity;
import com.myvg.myvg.EntityModel.VideogameEntity;
import com.myvg.myvg.DTO.ReviewDTO;
import org.springframework.stereotype.Service;

//IT'S REVIEW SERVICE RESPONSABILITY TO UPDATES THE REFERENCES 
//IN VIDEOGAME ENTITY AND USER ENTITY (USER ENTITY DONT HAVE ANY REFERENCES TO REVIEW YET)
@Service
public class ReviewService {

    //SERVICES ARE DECOUPLED. DO NOT COMMUNICATE WITH EACH OTHER Services()DAO
    //ONLY COMMUNICATE WITH DAOs 

    private final ReviewDAO reviewDAO;
    private final UserDAO userDAO;
    private final VideogameDAO videogameDAO;

    public ReviewService(ReviewDAO reviewDAO, UserDAO userDAO, VideogameDAO videogameDAO) {
        this.reviewDAO = reviewDAO;
        this.userDAO = userDAO;
        this.videogameDAO = videogameDAO;
    }

    public ReviewEntity postReview(ReviewDTO reviewDTO) {
        if (validateReviewDTO(reviewDTO)) {
            ReviewEntity reviewEntity = reviewDAO.create(new ReviewEntity(reviewDTO));

            videogameDAO.findById(reviewDTO.getVideogameDTO().getId())
                .ifPresent(vgEntity -> {
                    vgEntity.getReviews().add(reviewEntity);
                    videogameDAO.update(vgEntity);
                });

            return reviewEntity;
        }
        return null;
    }

    private boolean validateReviewDTO(ReviewDTO reviewDTO) {
        if (reviewDTO == null) {
            throw new IllegalArgumentException("Review cannot be null");
        }

        if (reviewDTO.getUserDTO() == null || reviewDTO.getUserDTO().getId() == null) {
            throw new IllegalArgumentException("User information is required");
        }

        if (reviewDTO.getVideogameDTO() == null || reviewDTO.getVideogameDTO().getId() == null) {
            throw new IllegalArgumentException("Videogame information is required");
        }

        if (reviewDTO.getComment() == null || reviewDTO.getComment().trim().isEmpty()) {
            throw new IllegalArgumentException("Review comment cannot be empty");
        }

        if (reviewDTO.getRating() < 1 || reviewDTO.getRating() > 10) {
            throw new IllegalArgumentException("Rating must be between 1 and 10");
        }

        return true;
    }

    public List<ReviewEntity> getReviewsByUser(String userId) {
        Optional<UserEntity> user = userDAO.findById(userId);
        if (user.isPresent()) {
            return reviewDAO.findByUser(user.get());
        }
        return List.of();
    }

    public List<ReviewEntity> getReviewsByVideogame(String videogameId) {
        Optional<VideogameEntity> videogame = videogameDAO.findById(videogameId);
        if (videogame.isPresent()) {
            return reviewDAO.findByVideogame(videogame.get());
        }
        return List.of();
    }

    public Optional<ReviewEntity> getReviewById(String id) {
        return reviewDAO.findById(id);
    }

    public List<ReviewEntity> getAllReviews() {
        return reviewDAO.findAll();
    }

    public void deleteReview(String id) {
        reviewDAO.delete(id);
    }

    public Optional<ReviewEntity> updateReview(String id, ReviewEntity reviewUpdated) {
        // Validate rating is between 1-5
        if (reviewUpdated.getRating() < 1 || reviewUpdated.getRating() > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        
        return reviewDAO.update(reviewUpdated);
    }

    public Optional<ReviewEntity> likeReview(String Id, String userId) {
        Optional<ReviewEntity> reviewOptional = reviewDAO.findById(Id);
        if (reviewOptional.isPresent()) {
            ReviewEntity review = reviewOptional.get();
            review.incrementLikes();
            return reviewDAO.update(review);
        } else {
            throw new IllegalArgumentException("Review not found");
        }
    }

    public List<ReviewEntity> getAllReviewsSortedByLikes() {
        List<ReviewEntity> reviews = reviewDAO.findAll();
        reviews.sort(Comparator.comparingInt(ReviewEntity::getLikes).reversed());
        return reviews;
    }
}
