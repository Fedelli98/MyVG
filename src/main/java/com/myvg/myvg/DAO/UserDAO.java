package com.myvg.myvg.DAO;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.myvg.myvg.EntityModel.ReviewEntity;
import com.myvg.myvg.EntityModel.UserEntity;

@Repository
public class UserDAO implements IDAO<UserEntity> {
    @Autowired
    private final MongoTemplate mongoTemplate;

    public UserDAO(MongoTemplate mT) {
        this.mongoTemplate = mT;
    }

    public Optional<UserEntity> create(UserEntity userEntity) {
        return Optional.ofNullable(mongoTemplate.save(userEntity));
    }

    public Optional<UserEntity> read(String id) {
        return Optional.ofNullable(mongoTemplate.findById(id, UserEntity.class));
    }

    public boolean update(UserEntity userUpdated) {
        Optional<UserEntity> reviewDB = read(userUpdated.getId());
        if (reviewDB.isPresent()) {
            mongoTemplate.save(userUpdated);
            return true;
        }
        else{
            return false;
        }
    }

    public boolean delete(String id) {
        Optional<UserEntity> reviewDB = read(id);
        if (reviewDB.isPresent()) {
            mongoTemplate.remove(reviewDB.get());
            return true;
        }
        else{
            return false;
        }
    }

    public List<UserEntity> readAll() {
        return mongoTemplate.findAll(UserEntity.class);
    }
    
    public Optional<UserEntity> readUserByUsername(String username) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        return Optional.ofNullable(mongoTemplate.findOne(query, UserEntity.class));
    }

    public Optional<UserEntity> updateAvatarID(String username, int avatarID) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        UserEntity user = mongoTemplate.findOne(query, UserEntity.class);
        user.setAvatarId(avatarID);
        mongoTemplate.save(user);
        return Optional.ofNullable(user);
    }
}
