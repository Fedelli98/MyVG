package com.myvg.myvg.Services;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.myvg.myvg.Models.User;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    private final MongoTemplate mongoTemplate;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserService(MongoTemplate mT) {
        this.mongoTemplate = mT;
    }

    public void registerUser(String username, String password, String email) {
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(username, encodedPassword, email);
        mongoTemplate.save(user);
    }

    public boolean loginUser(String username, String password) {
        
        Query query2 = new Query();
        query2.addCriteria(Criteria.where("username").is(username));
    

        List<User> users = mongoTemplate.find(query2, User.class);
        if (users != null) {
            String encodedPassword = users.get(0).getPassword();
            return passwordEncoder.matches(password, encodedPassword);
        }
        return false;
    }
}