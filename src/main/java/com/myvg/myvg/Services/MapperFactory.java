package com.myvg.myvg.Services;

import java.util.List;

import com.myvg.myvg.DTO.VideogameDTO;
import com.myvg.myvg.EntityModel.VideogameEntity;



public class MapperFactory {
    
    @SuppressWarnings("unchecked")
    public static <TSource, TDestination> Mapper<TSource, TDestination> createMapper(
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



