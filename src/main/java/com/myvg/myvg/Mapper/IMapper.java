package com.myvg.myvg.Mapper;

public interface IMapper<TSource, TDestination> extends IMapperBase {
    TDestination map(TSource source, TDestination destination);
}
