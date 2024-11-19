package com.myvg.myvg.Services;

import java.util.List;

import com.myvg.myvg.DTO.VideogameDTO;
import com.myvg.myvg.EntityModel.VideogameEntity;
import java.util.function.BiConsumer;

public class MapperTest {

    MapperTest(){
        VideogameDTO videogameDTO = new VideogameDTO();
        VideogameEntity videogameEntity = new VideogameEntity();

        MapperProfile<VideogameEntity, VideogameDTO> mapperBuilder = new MapperProfile<>();
        List<BiConsumer<VideogameEntity, VideogameDTO>> mappings = List.of(
            (entity, dto) -> dto.setId(entity.getId()),
            (entity, dto) -> dto.setTitle(entity.getTitle())
        );

        Mapper<VideogameEntity, VideogameDTO> mapper = mapperBuilder.createMapper(mappings);

        VideogameDTO videogameEntityCoverted = mapper.map(videogameEntity, videogameDTO);

        System.out.println(videogameEntityCoverted.getId());
    }
}
