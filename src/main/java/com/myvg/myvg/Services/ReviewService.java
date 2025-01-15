package com.myvg.myvg.Services;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Comparator;

import com.myvg.myvg.DAO.ReviewDAO;
import com.myvg.myvg.DAO.UserDAO;
import com.myvg.myvg.DAO.VideogameDAO;
import com.myvg.myvg.EntityModel.ReviewEntity;
import com.myvg.myvg.EntityModel.UserEntity;
import com.myvg.myvg.EntityModel.VideogameEntity;

import com.myvg.myvg.Mapper.MapperProfileFactory;
import com.myvg.myvg.Mapper.MapperProfileFactory.MapperProfileEnum;
import com.myvg.myvg.Mapper.MapperProfile;
import com.myvg.myvg.DTO.ReviewDTO;

import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    private final ReviewDAO reviewDAO;
    private final UserDAO userDAO;
    private final VideogameDAO videogameDAO;

    private final MapperProfile mapperReview = MapperProfileFactory.createMapperProfile(MapperProfileEnum.REVIEW);

    public ReviewService(ReviewDAO reviewDAO, UserDAO userDAO, VideogameDAO videogameDAO) {
        this.reviewDAO = reviewDAO;
        this.userDAO = userDAO;
        this.videogameDAO = videogameDAO;
    }

    public boolean postReview(ReviewDTO reviewDTO) throws IllegalArgumentException
    {
        if (validateReviewDTO(reviewDTO)) 
        {
            //get user
            var user = userDAO.readUserByUsername(reviewDTO.getUsername());
            if(user.isEmpty()) return false;

            //get videogame
            var videogame = videogameDAO.readByTitle(reviewDTO.getVideogameTitle());
            if(videogame.isEmpty()) return false;

            //check if user already posted a review
            for(var review : videogame.get().getReviews())
            {
                if(review.getUsername().equals(reviewDTO.getUsername()))
                {
                    throw new IllegalArgumentException("Already posted a review for this game");
                }
            }

            //insert review
            Optional<ReviewEntity> reviewPosted = reviewDAO.create(mapperReview.map(reviewDTO, new ReviewEntity()));
            //update id on DTO;
            if(reviewPosted.isPresent()) 
            {
                reviewDTO.setId(reviewPosted.get().getId());
                //update videogame entity
                videogameDAO.readByTitle(reviewPosted.get().getVideogameTitle())
                .ifPresent(vgEntity -> {
                    vgEntity.getReviews().add(reviewPosted.get());
                    videogameDAO.update(vgEntity);
                });
                
                return true;
            }
        }
        return false;
    }

    private boolean validateReviewDTO(ReviewDTO reviewDTO) throws IllegalArgumentException
    {
        if (reviewDTO == null) {
            throw new IllegalArgumentException("Review cannot be null");
        }

        if (reviewDTO.getUsername() == null || reviewDTO.getUsername() == null) {
            throw new IllegalArgumentException("User information is required");
        }

        if (reviewDTO.getVideogameTitle() == null || reviewDTO.getVideogameTitle() == null) {
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

    public List<ReviewDTO> getReviewsByUserId(String userId) {
        Optional<UserEntity> user = userDAO.read(userId);
        if (user.isPresent()) {
            var reviews = new ArrayList<ReviewDTO>();
            for(ReviewEntity review : reviewDAO.readByUser(user.get().getUsername()))
            {
                reviews.add(mapperReview.map(review, new ReviewDTO()));
            }
            return reviews;
        }
        return List.of();
    }

    public List<ReviewEntity> getReviewsByVideogameId(String videogameId) {
        Optional<VideogameEntity> videogame = videogameDAO.read(videogameId);
        if (videogame.isPresent()) {
            return reviewDAO.readByVideogame(videogame.get().getTitle());
        }
        return List.of();
    }

    public Optional<ReviewEntity> getReviewById(String id) {
        return reviewDAO.read(id);
    }

    public List<ReviewEntity> getAllReviews() {
        return reviewDAO.readAll();
    }

    public void delete(String id) {
        Optional<ReviewEntity> reviewEntity = getReviewById(id);
        if(reviewEntity.isPresent())
        {
            //update videogame
            Optional<VideogameEntity> vgEntity = videogameDAO.readByTitle(reviewEntity.get().getVideogameTitle());
                if(vgEntity.isPresent()){
                    vgEntity.get().getReviews().remove(reviewEntity.get());
                    videogameDAO.update(vgEntity.get());
                }

            //update review
            reviewDAO.delete(id);
        };
    }

    public boolean update(ReviewDTO reviewUpdated) {
        // Validate rating is between 1-5
        if (reviewUpdated.getRating() < 1 || reviewUpdated.getRating() > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }

        var reviewEntity = mapperReview.map(reviewUpdated, new ReviewEntity());
        return reviewDAO.update(reviewEntity);
    }

    public boolean likeReview(String Id, String userId) {
        Optional<ReviewEntity> reviewOptional = reviewDAO.read(Id);
        if (reviewOptional.isPresent()) {
            ReviewEntity review = reviewOptional.get();
            review.incrementLikes();
            return reviewDAO.update(review);
        } else {
            throw new IllegalArgumentException("Review not found");
        }
    }

    public List<ReviewEntity> getAllReviewsSortedByLikes() {
        List<ReviewEntity> reviews = reviewDAO.readAll();
        reviews.sort(Comparator.comparingInt(ReviewEntity::getLikes).reversed());
        return reviews;
    }
}
