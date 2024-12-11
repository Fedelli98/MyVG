package com.myvg.myvg.Mapper;

import java.util.List;
import java.util.ArrayList;
import java.util.function.BiConsumer;



public class Mapper<TSource, TDestination> implements IMapper<TSource, TDestination>
{
    private final Class<TSource> sourceType;
    private final Class<TDestination> destinationType;

    public Mapper(Class<TSource> sourceType, Class<TDestination> destinationType) {
        this.sourceType = sourceType;
        this.destinationType = destinationType;
    }

    private List<BiConsumer<TSource, TDestination>> expressions = new ArrayList<>();

    public List<BiConsumer<TSource, TDestination>> getExpressions(){
        return expressions;
    }

    public void setExpressions(List<BiConsumer<TSource, TDestination>> expressions){
        this.expressions = expressions;
    }

    @Override
    public Class<TSource> getSourceType(){
        return sourceType;
    }

    @Override
    public Class<TDestination> getDestinationType(){
        return destinationType;
    }
    

    @Override
    public TDestination map(TSource source, TDestination destination){
        for(BiConsumer<TSource, TDestination> exp : expressions)
        {
            exp.accept(source, destination);
        }
        return destination;
    }

}
