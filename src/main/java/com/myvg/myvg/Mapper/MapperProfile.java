package com.myvg.myvg.Mapper;

import java.util.List;
import java.util.ArrayList;

    
public class MapperProfile{
    private List<IMapper<?, ?>> mappers = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public <TSource, TDestination> TDestination map(TSource source, TDestination destination)
    {
        for (IMapperBase mapper : mappers) {
            if ((IMapper<TSource, TDestination>) mapper instanceof IMapper<TSource, TDestination>) {
                var mapToUse = (IMapper<TSource, TDestination>) mapper;
                return mapToUse.map(source, destination);
            }
        }

        throw new IllegalArgumentException("Mapper not found for: " + source.getClass().getName() + " -> " + destination.getClass().getName());
    }

    public List<IMapper<?, ?>> getMappers(){
        return mappers;
    }

    public void setMappers(List<IMapper<?, ?>> mappers){
        this.mappers = mappers;
    }
}
