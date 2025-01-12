package com.myvg.myvg.Services;
import org.springframework.stereotype.Service;

import com.myvg.myvg.DTO.UserDTO;
import com.myvg.myvg.DTO.VideogameDTO;
import java.util.List;
import java.util.ArrayList;

//add service scope singleton? and remove the static methods
//or keep the singleton old way
//@Service
public class AppContext {
    private static AppContext instance;
    private UserDTO currentUser;
    private VideogameDTO currentVideogame;
    private List<VideogameDTO> currentQuery;

    private AppContext() {
        currentQuery = new ArrayList<>();
    }

    public static AppContext getInstance() {
        if (instance == null) {
            instance = new AppContext();
        }
        return instance;
    }

    public UserDTO getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(UserDTO user) {
        this.currentUser = user;
    }

    public VideogameDTO getCurrentVideogame() {
        return currentVideogame;
    }

    public void setCurrentVideogame(VideogameDTO videogame) {
        this.currentVideogame = videogame;
    }

    public List<VideogameDTO> getCurrentQuery() {
        return currentQuery;
    }

    public void setCurrentQuery(List<VideogameDTO> query) {
        this.currentQuery = query;
    }

    public void clearCurrentQuery() {
        this.currentQuery.clear();
    }

    public void clearCurrentVideogame() {
        this.currentVideogame = null;
    }   

    public void clearCurrentUser() {
        this.currentUser = null;
    }

}
