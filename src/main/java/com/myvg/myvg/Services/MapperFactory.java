package com.myvg.myvg.Services;

import java.util.List;

import com.myvg.myvg.DTO.VideogameDTO;
import com.myvg.myvg.EntityModel.VideogameEntity;
import com.myvg.myvg.DTO.ReviewDTO;
import com.myvg.myvg.EntityModel.ReviewEntity;
import com.myvg.myvg.DTO.UserDTO;
import com.myvg.myvg.EntityModel.UserEntity;

enum MapperType {
    VIDEOGAME_DTO_TO_ENTITY(VideogameDTO.class, VideogameEntity.class),
    REVIEW_DTO_TO_ENTITY(ReviewDTO.class, ReviewEntity.class),
    USER_DTO_TO_ENTITY(UserDTO.class, UserEntity.class),
    ENTITY_TO_VIDEOGAME_DTO(VideogameEntity.class, VideogameDTO.class),
    ENTITY_TO_REVIEW_DTO(ReviewEntity.class, ReviewDTO.class),
    ENTITY_TO_USER_DTO(UserEntity.class, UserDTO.class);

    private final Class<?> sourceType;
    private final Class<?> destinationType;

    MapperType(Class<?> sourceType, Class<?> destinationType) {
        this.sourceType = sourceType;
        this.destinationType = destinationType;
    }

    public Class<?> getSourceType() {
        return sourceType;
    }

    public Class<?> getDestinationType() {
        return destinationType;
    }
}

public class MapperFactory {
    
    @SuppressWarnings("unchecked")
    public static <TSource, TDestination> Mapper<TSource, TDestination> createMapper(MapperType mapperType) {
        
        switch(mapperType) {
            case VIDEOGAME_DTO_TO_ENTITY:
                return (Mapper<TSource, TDestination>) new Mapper<VideogameDTO, VideogameEntity>(List.of(
                    (dto, entity) -> entity.setTitle(dto.getTitle()),
                    (dto, entity) -> entity.setReleaseYear(100),
                    (dto, entity) -> entity.setPlatform(dto.getPlatform()),
                    (dto, entity) -> entity.setReviews(dto.getReviews())
                ));
            
            case REVIEW_DTO_TO_ENTITY:
                return (Mapper<TSource, TDestination>) new Mapper<ReviewDTO, ReviewEntity>(List.of(
                    (dto, entity) -> entity.setComment(dto.getComment()),
                    (dto, entity) -> entity.setRating(dto.getRating()),
                    (dto, entity) -> entity.setUsername(dto.getUserDTO().getUsername())
                ));

            case USER_DTO_TO_ENTITY:
                return (Mapper<TSource, TDestination>) new Mapper<UserDTO, UserEntity>(List.of(
                    (dto, entity) -> entity.setUsername(dto.getUsername()),
                    (dto, entity) -> entity.setEmail(dto.getEmail())/* ,
                    (dto, entity) -> entity.setWishlist(dto.getWishlist()) */
                ));
                
            case ENTITY_TO_VIDEOGAME_DTO:
                return (Mapper<TSource, TDestination>) new Mapper<VideogameEntity, VideogameDTO>(List.of(
                    (entity, dto) -> dto.setTitle(entity.getTitle()),
                    (entity, dto) -> dto.setReleaseYear(entity.getReleaseYear()),
                    (entity, dto) -> dto.setPlatform(entity.getPlatform()),
                    (entity, dto) -> dto.setReviews(entity.getReviews())
                ));

            case ENTITY_TO_REVIEW_DTO:
                return (Mapper<TSource, TDestination>) new Mapper<ReviewEntity, ReviewDTO>(List.of(
                    (entity, dto) -> dto.setComment(entity.getComment()),
                    (entity, dto) -> dto.setRating(entity.getRating())/* ,
                    (entity, dto) -> dto.setUsername(entity.getUsername()) */
                ));

            case ENTITY_TO_USER_DTO:
                return (Mapper<TSource, TDestination>) new Mapper<UserEntity, UserDTO>(List.of(
                    (entity, dto) -> dto.setUsername(entity.getUsername()),
                    (entity, dto) -> dto.setEmail(entity.getEmail())/* ,
                    (entity, dto) -> dto.setWishlist(entity.getWishlist()) */
                ));
                
            default:
                throw new IllegalArgumentException(
                    "No mapper found for type: " + mapperType
                );
        }
    }
/*
 * public static <TSource, TDestination> Mapper<TSource, TDestination> createMapper(
            Class<TSource> sourceType, 
            Class<TDestination> destinationType) {
            if (sourceType == VideogameDTO.class && destinationType == VideogameEntity.class) {
            return (Mapper<TSource, TDestination>) new Mapper<VideogameDTO, VideogameEntity>(List.of(
                (dto, entity) -> entity.setTitle(dto.getTitle()),
                (dto, entity) -> entity.setReleaseYear(100),
                (dto, entity) -> entity.setPlatform(dto.getPlatform()),
                (dto, entity) -> entity.setReviews(dto.getReviews())
            ));
        }

        // Qui puoi aggiungere altri mapping per altri tipi di oggetti
        //if(sourceType == Source.class && destinationType == Destination.class)
        
        throw new IllegalArgumentException(
            "No mapper found for source: " + sourceType.getName() + 
            " and destination: " + destinationType.getName()
        );
    }
}
 */
}

