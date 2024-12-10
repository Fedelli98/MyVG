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
                return new MapperProfile() {{
                    setMappers(List.of(
                        new Mapper<VideogameDTO, VideogameEntity>() {{
                            setExpressions(List.of(
                                (source, destination) -> destination.setId(source.getId()),
                                (source, destination) -> destination.setTitle(source.getTitle()),
                                (source, destination) -> destination.setGenre(source.getGenre()),
                                (source, destination) -> destination.setReleaseYear(source.getReleaseYear()),
                                (source, destination) -> destination.setPlatform(source.getPlatform()),
                                (source, destination) -> destination.setReviews(source.getReviews())
                            ));
                        }},
                        new Mapper<VideogameEntity, VideogameDTO>() {{
                            setExpressions(List.of(
                                (source, destination) -> destination.setId(source.getId()),
                                (source, destination) -> destination.setTitle(source.getTitle()),
                                (source, destination) -> destination.setGenre(source.getGenre()),
                                (source, destination) -> destination.setReleaseYear(source.getReleaseYear()),
                                (source, destination) -> destination.setPlatform(source.getPlatform()),
                                (source, destination) -> destination.setReviews(source.getReviews())
                            ));
                        }}
                    ));
                }};
            case USER:
                return new MapperProfile() {{
                    setMappers(List.of(
                        new Mapper<UserDTO, UserEntity>() {{
                            setExpressions(List.of(
                                (source, destination) -> destination.setId(source.getId()),
                                (source, destination) -> destination.setUsername(source.getUsername()),
                                (source, destination) -> destination.setEmail(source.getEmail()),
                                (source, destination) -> destination.setAvatarId(source.getAvatarID()),
                                (source, destination) -> destination.setWishlist(source.getWishlist().stream()
                                    .map(VideogameEntity::new)
                                    .collect(Collectors.toList()))
                            ));
                        }},
                        new Mapper<UserEntity, UserDTO>() {{
                            setExpressions(List.of(
                                (source, destination) -> destination.setId(source.getId()),
                                (source, destination) -> destination.setUsername(source.getUsername()),
                                (source, destination) -> destination.setEmail(source.getEmail()),
                                (source, destination) -> destination.setAvatarID(source.getAvatarId()),
                                (source, destination) -> destination.setWishlist(source.getWishlist().stream()
                                    .map(VideogameDTO::new)
                                    .collect(Collectors.toList()))
                            ));
                        }}
                    ));
                }};
            case REVIEW:
                return new MapperProfile() {{
                    setMappers(List.of(
                        new Mapper<ReviewDTO, ReviewEntity>() {{
                            setExpressions(List.of(
                                (source, destination) -> destination.setId(source.getId()),
                                (source, destination) -> destination.setUsername(source.getUserDTO().getUsername()),
                                (source, destination) -> destination.setVideogameTitle(source.getVideogameDTO().getTitle()),
                                (source, destination) -> destination.setRating(source.getRating()),
                                (source, destination) -> destination.setComment(source.getComment())
                            ));
                        }},
                        new Mapper<ReviewEntity, ReviewDTO>() {{
                            setExpressions(List.of(
                                (source, destination) -> destination.setId(source.getId()),
                                (source, destination) -> destination.setRating(source.getRating()),
                                (source, destination) -> destination.setComment(source.getComment()),
                                (source, destination) -> destination.setUserDTO(AppContext.getInstance().getCurrentUser()),
                                (source, destination) -> destination.setVideogameDTO(AppContext.getInstance().getCurrentVideogame())
                            ));
                        }}
                    ));
                }};
            default:
                return null;
        }
    }
}
