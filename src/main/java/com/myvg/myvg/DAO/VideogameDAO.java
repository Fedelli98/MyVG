package com.myvg.myvg.DAO;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.myvg.myvg.EntityModel.VideogameEntity;

@Repository
public class VideogameDAO {
    @Autowired
    private final MongoTemplate mongoTemplate;

    public VideogameDAO(MongoTemplate mT) {
        this.mongoTemplate = mT;
    }


    public void create(VideogameEntity videogameDTO) {
        mongoTemplate.save(videogameDTO);   
    }

    public Optional<VideogameEntity> findByTitle(String title) {
        Query query = new Query();
        query.addCriteria(Criteria.where("title").is(title));

        return Optional.ofNullable(mongoTemplate.findOne(query, VideogameEntity.class));
    }

    public Optional<VideogameEntity> findById(String id) {
        return Optional.ofNullable(mongoTemplate.findById(id, VideogameEntity.class));
    }

    public List<VideogameEntity> findAll() {
        return mongoTemplate.findAll(VideogameEntity.class);
    }

    public List<VideogameEntity> findByGenre(String genre) {
        Query query = new Query();
        query.addCriteria(Criteria.where("genre").is(genre));
        return mongoTemplate.find(query, VideogameEntity.class);
    }   

    public List<VideogameEntity> findByPlatform(String platform) {
        Query query = new Query();
        query.addCriteria(Criteria.where("platform").is(platform));
        return mongoTemplate.find(query, VideogameEntity.class);
    }

    public Optional<VideogameEntity> update(VideogameEntity videogameUpdated) {
        if (videogameUpdated == null || videogameUpdated.getId() == null) {
            return Optional.empty();
        }
        
        // Check if the entity exists
        Optional<VideogameEntity> existing = findById(videogameUpdated.getId());
        if (existing.isEmpty()) {
            return Optional.empty();
        }
        
        return Optional.of(mongoTemplate.save(videogameUpdated));
    }

    public void delete(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        mongoTemplate.remove(query, VideogameEntity.class);
    }
}
