package com.myvg.myvg.DAO;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.myvg.myvg.Models.User;

@Repository
public class UserDAO {
    @Autowired
    private final MongoTemplate mongoTemplate;

    public UserDAO(MongoTemplate mT) {
        this.mongoTemplate = mT;
    }

    public void CreateUser(String username, String password, String email, int avatarID) {
        User user = new User(username, password, email, avatarID);
        mongoTemplate.save(user);
    }
    
    public Optional<User> findUserByUsername(String username) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        return Optional.ofNullable(mongoTemplate.findOne(query, User.class));
    }

    public Optional<User> findById(String id) {
        return Optional.ofNullable(mongoTemplate.findById(id, User.class));
    }
}
