package com.myvg.myvg.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myvg.myvg.DAO.ReviewDAO;
import com.myvg.myvg.DAO.UserDAO;
import com.myvg.myvg.DAO.VideogameDAO;
import com.myvg.myvg.DTO.VideogameDTO;
import com.myvg.myvg.EntityModel.VideogameEntity;
import com.myvg.myvg.Mapper.MapperProfile;
import com.myvg.myvg.Mapper.MapperProfileFactory;
import com.myvg.myvg.Mapper.MapperProfileFactory.MapperProfileEnum;

import java.util.List;
import java.util.Optional;

@Service
public class VideogameService {
    
    @Autowired
    private final VideogameDAO videogameDAO;

    @Autowired
    private final UserDAO userDAO;

    private final MapperProfile mapperProfile;

    

    public VideogameService(VideogameDAO videogameDAO, UserDAO userDAO) {
        this.videogameDAO = videogameDAO;
        this.userDAO = userDAO;

        this.mapperProfile = MapperProfileFactory.createMapperProfile(MapperProfileEnum.VIDEOGAME);
    }

    public Optional<VideogameEntity> getGameById(String id) {
        return videogameDAO.read(id);
    }

    public Optional<VideogameEntity> getGameByTitle(String title) {
        return videogameDAO.readByTitle(title);
    }

    public List<VideogameEntity> getAllGames() {
        return videogameDAO.readAll();
    }

    public List<VideogameEntity> getGamesByGenre(String genre) {
        return videogameDAO.readByGenre(genre);
    }

    public List<VideogameEntity> getGamesByPlatform(String platform) {
        return videogameDAO.readByPlatform(platform);
    }

    public boolean update(VideogameEntity videogame) {
        return videogameDAO.update(videogame);
    }

    public void delete(String id) {
        videogameDAO.delete(id);
    }

    public void addToWishlist(String userId, VideogameDTO videogameDTO) {
        userDAO.read(userId).ifPresent(user -> {
            VideogameEntity videogameEntity = mapperProfile.map(videogameDTO, new VideogameEntity());
            if (!user.getWishlist().contains(videogameEntity)) {
                user.getWishlist().add(videogameEntity);
                userDAO.update(user);
            }
        });
    }

}

