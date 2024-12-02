package com.myvg.myvg.Mapper;

import com.myvg.myvg.EntityModel.VideogameEntity;
import com.myvg.myvg.DTO.VideogameDTO;

import java.util.List;

enum MapperProfileEnum {
    VIDEOGAME
}

//MapperProfile vgMapper;

// vgMapper = MapperProfileFactory.createMapperProfile(MapperProfileEnum.VIDEOGAME)

// dest = vgMapper.map(source, destination);

public class MapperProfileFactory {

    public static MapperProfile createMapperProfile(MapperProfileEnum profile) {
        switch (profile) {
            case VIDEOGAME:
                return new MapperProfile() {{
                    setMappers(List.of(
                        new Mapper<VideogameDTO, VideogameEntity>() {{
                        setExpressions(List.of(
                            (source, destination) -> destination.setId(source.getId()),
                            (source, destination) -> destination.setReleaseYear(source.getReleaseYear())
                        ));
                        new Mapper<VideogameDTO, VideogameEntity>(){{
                            setExpressions(List.of(
                                (source, destination) -> destination.setId(source.getId()),
                                (source, destination) -> destination.setReleaseYear(source.getReleaseYear())
                            ));
                        }};
                    }}));
                }};


            default:
                return null;
        }
    }
}
