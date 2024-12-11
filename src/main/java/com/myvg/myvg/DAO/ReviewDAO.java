package com.myvg.myvg.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.myvg.myvg.EntityModel.ReviewEntity;
import java.util.List;
import java.util.Optional;

import com.myvg.myvg.EntityModel.UserEntity;
import com.myvg.myvg.EntityModel.VideogameEntity;


@Repository
public class ReviewDAO {
    @Autowired
    private final MongoTemplate mongoTemplate;
    
    public ReviewDAO(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public ReviewEntity create(ReviewEntity reviewEntity) {
        return mongoTemplate.save(reviewEntity);
    }

    public Optional<ReviewEntity> findById(String id) {
        return Optional.ofNullable(mongoTemplate.findById(id, ReviewEntity.class));
    }

    public List<ReviewEntity> findByUser(UserEntity userEntity) {
        Query query = new Query();
        query.addCriteria(Criteria.where("user").is(userEntity));
        return mongoTemplate.find(query, ReviewEntity.class);
    }

    public List<ReviewEntity> findByVideogame(VideogameEntity videogameEntity) {
        Query query = new Query();
        query.addCriteria(Criteria.where("videogame").is(videogameEntity));
        return mongoTemplate.find(query, ReviewEntity.class);
    }

    public List<ReviewEntity> findAll() {
        return mongoTemplate.findAll(ReviewEntity.class);
    }

    public void delete(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        mongoTemplate.remove(query, ReviewEntity.class);
    }

    public Optional<ReviewEntity> update(ReviewEntity reviewUpdated) {
        Optional<ReviewEntity> reviewDB = findById(reviewUpdated.getId());
        if (reviewDB.isPresent()) {
            ReviewEntity updatedReview = reviewDB.get();
            updatedReview.setUsername(reviewUpdated.getUsername());
            updatedReview.setVideogameTitle(reviewUpdated.getVideogameTitle());
            updatedReview.setRating(reviewUpdated.getRating());
            updatedReview.setComment(reviewUpdated.getComment());
            updatedReview.setLikes(reviewUpdated.getLikes());
            mongoTemplate.save(updatedReview);
            return Optional.of(updatedReview);
        }
        return Optional.empty();
    }
}

