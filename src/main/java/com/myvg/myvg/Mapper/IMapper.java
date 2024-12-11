package com.myvg.myvg.Mapper;

public interface IMapper<TSource, TDestination> extends IMapperBase {
    Class<TSource> getSourceType();
    Class<TDestination> getDestinationType();
    TDestination map(TSource source, TDestination destination);
}
