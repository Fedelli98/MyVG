package com.myvg.myvg.DAO;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.myvg.myvg.EntityModel.ReviewEntity;
import java.util.List;
import java.util.Optional;

@Repository
public class ReviewDAO implements IDAO<ReviewEntity> {

    private final MongoTemplate mongoTemplate;

    public ReviewDAO(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Optional<ReviewEntity> create(ReviewEntity reviewEntity) {
        return Optional.ofNullable(mongoTemplate.save(reviewEntity));
    }

    public Optional<ReviewEntity> read(String id) {
        return Optional.ofNullable(mongoTemplate.findById(id, ReviewEntity.class));
    }

    public boolean update(ReviewEntity reviewUpdated) {
        Optional<ReviewEntity> reviewDB = read(reviewUpdated.getId());
        if (reviewDB.isPresent()) {
            mongoTemplate.save(reviewUpdated);
            return true;
        }
        else{
            return false;
        }
    }

    public boolean delete(String id) {
        Optional<ReviewEntity> reviewDB = read(id);
        if (reviewDB.isPresent()) {
            mongoTemplate.remove(reviewDB.get());
            return true;
        }
        else{
            return false;
        }
    }

    public List<ReviewEntity> readAll() {
        return mongoTemplate.findAll(ReviewEntity.class);
    }

    public List<ReviewEntity> readByUser(String username) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        return mongoTemplate.find(query, ReviewEntity.class);
    }

    public List<ReviewEntity> readByVideogame(String videogameTitle) {
        Query query = new Query();
        query.addCriteria(Criteria.where("videogame").is(videogameTitle));
        return mongoTemplate.find(query, ReviewEntity.class);
    }


}

