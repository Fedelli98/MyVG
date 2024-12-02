package com.myvg.myvg.Mapper;

import java.util.ArrayList;
import java.util.function.BiConsumer;

import com.myvg.myvg.DTO.VideogameDTO;
import com.myvg.myvg.EntityModel.VideogameEntity;

import java.util.List;

public interface IMapper<TSource, TDestination> implements IMapperBase {
    TDestination map(TSource source, TDestination destination);
}

public interface IMapperBase{

}

public class Mapper<TSource, TDestination> implements IMapper<TSource, TDestination>
{
    private List<BiConsumer<TSource, TDestination>> expressions = new ArrayList<>();

    public List<BiConsumer<TSource, TDestination>> getExpressions(){
        return expressions;
    }

    public void setExpressions(List<BiConsumer<TSource, TDestination>> expressions){
        this.expressions = expressions;
    }

    @Override
    public TDestination map(TSource source, TDestination destination){
        for(BiConsumer<TSource, TDestination> exp : expressions)
        {
            expression.accept(source, destination);
        }
        return destination;
    }

}
