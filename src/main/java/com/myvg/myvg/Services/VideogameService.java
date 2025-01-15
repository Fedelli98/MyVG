package com.myvg.myvg.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myvg.myvg.DAO.UserDAO;
import com.myvg.myvg.DAO.VideogameDAO;
import com.myvg.myvg.DTO.VideogameDTO;
import com.myvg.myvg.EntityModel.VideogameEntity;
import com.myvg.myvg.Mapper.MapperProfile;
import com.myvg.myvg.Mapper.MapperProfileFactory;
import com.myvg.myvg.Mapper.MapperProfileFactory.MapperProfileEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VideogameService {
    
    @Autowired
    private final VideogameDAO videogameDAO;

    @Autowired
    private final UserDAO userDAO;

    private final MapperProfile mapperVg;


    public VideogameService(VideogameDAO videogameDAO, UserDAO userDAO) {
        this.videogameDAO = videogameDAO;
        this.userDAO = userDAO;

        this.mapperVg = MapperProfileFactory.createMapperProfile(MapperProfileEnum.VIDEOGAME);
    }

    public Optional<VideogameDTO> getGameById(String id) {
        return Optional.ofNullable(mapperVg.map(videogameDAO.read(id), new VideogameDTO()));
    }

    public Optional<VideogameDTO> getGameByTitle(String title) {
        return Optional.ofNullable(mapperVg.map(videogameDAO.readByTitle(title), new VideogameDTO()));
    }

    public List<VideogameDTO> getAllGames() {
        List <VideogameDTO> videogames = new ArrayList<VideogameDTO>();
        for (VideogameEntity videogame : videogameDAO.readAll()) {
            videogames.add(mapperVg.map(videogame, new VideogameDTO()));
        }
        return videogames;
    }

    public List<VideogameDTO> getGamesByGenre(String genre) {
        List <VideogameDTO> videogames = new ArrayList<VideogameDTO>();
        for (VideogameEntity videogame : videogameDAO.readByGenre(genre)) {
            videogames.add(mapperVg.map(videogame, new VideogameDTO()));
        }
        return videogames;
    }

    public List<VideogameDTO> getGamesByPlatform(String platform) {
        List <VideogameDTO> videogames = new ArrayList<VideogameDTO>();
        for (VideogameEntity videogame : videogameDAO.readByPlatform(platform)) {
            videogames.add(mapperVg.map(videogame, new VideogameDTO()));
        }
        return videogames;
    }

    public boolean update(VideogameDTO videogame) {
        return videogameDAO.update(mapperVg.map(videogame, new VideogameEntity()));
    }

    public void delete(String id) {
        videogameDAO.delete(id);
    }

    public void addToWishlist(String userId, VideogameDTO videogameDTO) {
        userDAO.read(userId).ifPresent(user -> {
            VideogameEntity videogameEntity = mapperVg.map(videogameDTO, new VideogameEntity());
            if (!user.getWishlist().contains(videogameEntity)) {
                user.getWishlist().add(videogameEntity);
                userDAO.update(user);
            }
        });
    }

}

