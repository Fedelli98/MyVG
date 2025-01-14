package com.myvg.myvg.DAO;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.myvg.myvg.EntityModel.ReviewEntity;
import com.myvg.myvg.EntityModel.VideogameEntity;

@Repository
public class VideogameDAO implements IDAO<VideogameEntity> {

    @Autowired
    private final MongoTemplate mongoTemplate;

    public VideogameDAO(MongoTemplate mT) {
        this.mongoTemplate = mT;
    }
    
    public Optional<VideogameEntity> create(VideogameEntity videogameEntity) {
        return Optional.ofNullable(mongoTemplate.save(videogameEntity));
    }
    
    public Optional<VideogameEntity> read(String id) {
        return Optional.ofNullable(mongoTemplate.findById(id, VideogameEntity.class));
    }

    public boolean update(VideogameEntity videogameUpdated) {
        Optional<VideogameEntity> vgDB = read(videogameUpdated.getId());
        if (vgDB.isPresent()) {
            mongoTemplate.save(videogameUpdated);
            return true;
        }
        else{
            return false;
        }
    }

    public boolean delete(String id) {
        Optional<VideogameEntity> vgDB = read(id);
        if (vgDB.isPresent()) {
            mongoTemplate.remove(vgDB.get());
            return true;
        }
        else{
            return false;
        }
    }

    public List<VideogameEntity> readAll() {
        return mongoTemplate.findAll(VideogameEntity.class);
    }
    
    public Optional<VideogameEntity> readByTitle(String title) {
        Query query = new Query();
        query.addCriteria(Criteria.where("title").is(title));

        return Optional.ofNullable(mongoTemplate.findOne(query, VideogameEntity.class));
    }

    public List<VideogameEntity> readByGenre(String genre) {
        Query query = new Query();
        query.addCriteria(Criteria.where("genre").is(genre));
        return mongoTemplate.find(query, VideogameEntity.class);
    }   

    public List<VideogameEntity> readByPlatform(String platform) {
        Query query = new Query();
        query.addCriteria(Criteria.where("platform").is(platform));
        return mongoTemplate.find(query, VideogameEntity.class);
    }

}
