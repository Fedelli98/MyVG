package com.myvg.myvg;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

import javafx.application.Application;

@SpringBootApplication(exclude = {MongoAutoConfiguration.class})
public class SpringBoot { 
    public static void main(String[] args) {
        Application.launch(MyvgApplication.class, args);
    }
}
