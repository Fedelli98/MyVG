package com.myvg.myvg.DAO;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.myvg.myvg.EntityModel.UserEntity;

@Repository
public class UserDAO {
    @Autowired
    private final MongoTemplate mongoTemplate;

    public UserDAO(MongoTemplate mT) {
        this.mongoTemplate = mT;
    }

    public void create(UserEntity userEntity) {
        mongoTemplate.save(userEntity);
    }
    
    public Optional<UserEntity> findUserByUsername(String username) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        return Optional.ofNullable(mongoTemplate.findOne(query, UserEntity.class));
    }

    public Optional<UserEntity> findById(String id) {
        return Optional.ofNullable(mongoTemplate.findById(id, UserEntity.class));
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
