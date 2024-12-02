package com.myvg.myvg.Mapper;

import com.myvg.myvg.Mapper.IMapperBase;


public interface IMapper<TSource, TDestination> extends IMapperBase {
    TDestination map(TSource source, TDestination destination);
}
