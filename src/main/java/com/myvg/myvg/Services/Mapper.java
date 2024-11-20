package com.myvg.myvg.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class Mapper<TSource, TDestination>
{
    private List<BiConsumer<TSource, TDestination>> mappings = new ArrayList<>();

    public Mapper(List<BiConsumer<TSource, TDestination>> mappings) {
        this.mappings = mappings;
    }

    public TDestination map(TSource source, TDestination destination) 
    {
        //TODO: create an empty or default istnace return default(TDestination)
        for (BiConsumer<TSource, TDestination> mapping : mappings) {
            mapping.accept(source, destination);
        }
        return destination;
    }
}
