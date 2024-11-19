package com.myvg.myvg.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class Mapper<TSource, TDestination> 
{
    private final List<BiConsumer<TSource, TDestination>> mappings = new ArrayList<>();

    public void addMapping(BiConsumer<TSource, TDestination> mapping) {
        mappings.add(mapping);
    }

    public TDestination map(TSource source, TDestination destination) 
    {
        for (BiConsumer<TSource, TDestination> mapping : mappings) {
            mapping.accept(source, destination);
        }
        return destination;
    }
}
