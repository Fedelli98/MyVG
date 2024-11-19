package com.myvg.myvg.Services;

import java.util.List;
import java.util.function.BiConsumer;

public class MapperProfile<TSource, TDestination> 
{
    public Mapper<TSource, TDestination> createMapper(List<BiConsumer<TSource, TDestination>> fieldMappings) 
    {
        Mapper<TSource, TDestination> mapper = new Mapper<>();
        for (BiConsumer<TSource, TDestination> mapping : fieldMappings) {
            mapper.addMapping(mapping);
        }
        return mapper;
    }
}



