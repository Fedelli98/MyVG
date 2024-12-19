package com.myvg.myvg.Mapper;

import com.myvg.myvg.EntityModel.VideogameEntity;

import javafx.beans.binding.SetExpression;

import com.myvg.myvg.DTO.VideogameDTO;
import com.myvg.myvg.DTO.ReviewDTO;
import com.myvg.myvg.DTO.UserDTO;
import com.myvg.myvg.EntityModel.ReviewEntity;
import com.myvg.myvg.EntityModel.UserEntity;
import com.myvg.myvg.Services.AppContext;


import java.util.List;
import java.util.stream.Collectors;


public class MapperProfileFactory {
    
    public enum MapperProfileEnum {
        VIDEOGAME,
        USER,
        REVIEW        
    }

    public static MapperProfile createMapperProfile(MapperProfileEnum profile) {
        switch (profile) {
            case VIDEOGAME:

            var mapperReview = MapperProfileFactory.createMapperProfile(MapperProfileFactory.MapperProfileEnum.REVIEW);
            
                return new MapperProfile() {{
                    setMappers(List.of(
                        new Mapper<VideogameDTO, VideogameEntity>(VideogameDTO.class, VideogameEntity.class) {{
                            setExpressions(List.of(
                                (source, destination) -> destination.setId(source.getId()),
                                (source, destination) -> destination.setTitle(source.getTitle()),
                                (source, destination) -> destination.setGenre(source.getGenre()),
                                (source, destination) -> destination.setReleaseYear(source.getReleaseYear()),
                                (source, destination) -> destination.setPlatform(source.getPlatform()),
                                (source, destination) -> destination.setReviews(source.getReviews().stream()
                                .map(reviewDTO -> mapperReview.map(reviewDTO, new ReviewEntity()))
                                .collect(Collectors.toList()))
                            ));
                        }},
                        new Mapper<VideogameEntity, VideogameDTO>(VideogameEntity.class, VideogameDTO.class) {{
                            setExpressions(List.of(
                                (source, destination) -> destination.setId(source.getId()),
                                (source, destination) -> destination.setTitle(source.getTitle()),
                                (source, destination) -> destination.setGenre(source.getGenre()),
                                (source, destination) -> destination.setReleaseYear(source.getReleaseYear()),
                                (source, destination) -> destination.setPlatform(source.getPlatform()),
                                (source, destination) -> destination.setReviews(source.getReviews().stream()
                                    .map(reviewEntity -> mapperReview.map(reviewEntity, new ReviewDTO()))
                                    .collect(Collectors.toList()))
                            ));
                        }}
                    ));
                }};
            case USER:

            var mapperVideogame = MapperProfileFactory.createMapperProfile(MapperProfileFactory.MapperProfileEnum.VIDEOGAME);

                return new MapperProfile() {{
                    setMappers(List.of(
                        new Mapper<UserDTO, UserEntity>(UserDTO.class, UserEntity.class) {{
                            setExpressions(List.of(
                                (source, destination) -> destination.setId(source.getId()),
                                (source, destination) -> destination.setUsername(source.getUsername()),
                                (source, destination) -> destination.setEmail(source.getEmail()),
                                (source, destination) -> destination.setAvatarId(source.getAvatarID()),
                                (source, destination) -> destination.setWishlist(source.getWishlist().stream()
                                    .map(videogameDTO -> mapperVideogame.map(videogameDTO, new VideogameEntity()))
                                    .collect(Collectors.toList()))
                            ));
                        }},
                        new Mapper<UserEntity, UserDTO>(UserEntity.class, UserDTO.class) {{
                            setExpressions(List.of(
                                (source, destination) -> destination.setId(source.getId()),
                                (source, destination) -> destination.setUsername(source.getUsername()),
                                (source, destination) -> destination.setEmail(source.getEmail()),
                                (source, destination) -> destination.setAvatarID(source.getAvatarId()),
                                (source, destination) -> destination.setWishlist(source.getWishlist().stream()
                                    .map(videogameEntity -> mapperVideogame.map(videogameEntity, new VideogameDTO()))
                                    .collect(Collectors.toList()))
                            ));
                        }}
                    ));
                }};
            case REVIEW:
                return new MapperProfile() {{
                    setMappers(List.of(
                        new Mapper<ReviewDTO, ReviewEntity>(ReviewDTO.class, ReviewEntity.class) {{
                            setExpressions(List.of(
                                (source, destination) -> destination.setId(source.getId()),
                                (source, destination) -> destination.setUsername(source.getUsername()),
                                (source, destination) -> destination.setVideogameTitle(source.getVideogameTitle()),
                                (source, destination) -> destination.setRating(source.getRating()),
                                (source, destination) -> destination.setComment(source.getComment())
                            ));
                        }},
                        new Mapper<ReviewEntity, ReviewDTO>(ReviewEntity.class, ReviewDTO.class) {{
                            setExpressions(List.of(
                                (source, destination) -> destination.setId(source.getId()),
                                (source, destination) -> destination.setRating(source.getRating()),
                                (source, destination) -> destination.setComment(source.getComment()),
                                (source, destination) -> destination.setUsername(source.getUsername()),
                                (source, destination) -> destination.setVideogameTitle(source.getVideogameTitle())
                            ));
                        }}
                    ));
                }};
            default:
                return null;
        }
    }
}
