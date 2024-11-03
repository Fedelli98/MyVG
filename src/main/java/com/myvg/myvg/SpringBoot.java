package com.myvg.myvg;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

import javafx.application.Application;

//Classe per lo start dell'Application di javafx
@SpringBootApplication(exclude = {MongoAutoConfiguration.class})
public class SpringBoot { 
    public static void main(String[] args) {
        Application.launch(MyvgApplication.class, args);
    }
}
