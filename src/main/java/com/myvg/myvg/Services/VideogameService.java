package com.myvg.myvg.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myvg.myvg.DAO.ReviewDAO;
import com.myvg.myvg.DAO.UserDAO;
import com.myvg.myvg.DAO.VideogameDAO;
import com.myvg.myvg.DTO.VideogameDTO;
import com.myvg.myvg.EntityModel.VideogameEntity;

import java.util.List;
import java.util.Optional;

@Service
public class VideogameService {
    
    @Autowired
    private final VideogameDAO videogameDAO;
    
    @Autowired 
    private final ReviewDAO reviewDAO;

    @Autowired
    private final UserDAO userDAO;

    private final Mapper<VideogameDTO, VideogameEntity> mapper;

    public VideogameService(VideogameDAO videogameDAO, ReviewDAO reviewDAO, UserDAO userDAO) {
        this.videogameDAO = videogameDAO;
        this.reviewDAO = reviewDAO;
        this.userDAO = userDAO;

        //TODO: move it to a config class
        //this.mapper = MapperFactory.createMapper(VideogameDTO.class, VideogameEntity.class);
        this.mapper = MapperFactory.createMapper(MapperType.VIDEOGAME_DTO_TO_ENTITY);
    }

    public void createVideoGame(VideogameDTO videogame) {
        //map dto to entity
        //VideogameEntity videogameEntity = new VideogameEntity(videogame);
        VideogameEntity videogameentity = new VideogameEntity();
        videogameentity.setGenre("Action");
        VideogameEntity videogameEntity = mapper.map(videogame, videogameentity);
        videogameDAO.create(videogameEntity);
    }
    
    //I MOVED THIS TO REVIEW SERVICE, FORSE MEGLIO SPOSTARLO QUI PER VARIEGARE UN PO;
    //OPPURE E' MEGLIO QUESTO DISACCOPPIAMENTO DI SERVIZI
    //SAREBBE DA CAMBIARE LO SCHEMA ER: VIDEOGAME HA UNA LISTA DI ID E REVIEW NON HA LE REF DI USER E VG

    // public void PostReview(ReviewDTO review) {
    //     Optional<VideogameEntity> videogame = videogameDAO.findById(review.getVideogameDTO().getId());

    //     if (videogame.isPresent()) {
    //         VideogameEntity game = videogame.get();
    //         List<ReviewEntity> reviews = game.getReviews();
            
    //         // Check if review already exists
    //         boolean reviewExists = reviews.stream()
    //             .anyMatch(r -> r.getId().equals(review.getId()));
            
    //         if (!reviewExists) {
    //             ReviewEntity reviewEntity = new ReviewEntity(review);
    //             reviews.add(reviewEntity);
    //             game.setReviews(reviews);
    //             videogameDAO.update(game);
    //         }
    //     }
    // }

    public Optional<VideogameEntity> findGameById(String id) {
        return videogameDAO.findById(id);
    }

    public Optional<VideogameEntity> findGameByTitle(String title) {
        return videogameDAO.findByTitle(title);
    }

    public List<VideogameEntity> findAllGames() {
        return videogameDAO.findAll();
    }

    public List<VideogameEntity> findGamesByGenre(String genre) {
        return videogameDAO.findByGenre(genre);
    }

    public List<VideogameEntity> findGamesByPlatform(String platform) {
        return videogameDAO.findByPlatform(platform);
    }

    public Optional<VideogameEntity> update(VideogameEntity videogame) {
        return videogameDAO.update(videogame);
    }

    public void deleteGame(String id) {
        videogameDAO.delete(id);
    }

    public void addToWishlist(String userId, VideogameDTO game) {
        userDAO.findById(userId).ifPresent(user -> {
            VideogameEntity gameEntity = new VideogameEntity(game);
            if (!user.getWishlist().contains(gameEntity)) {
                user.getWishlist().add(gameEntity);
                userDAO.update(user);
            }
        });
    }

}

