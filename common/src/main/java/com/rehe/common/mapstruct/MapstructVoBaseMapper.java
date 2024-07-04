package com.rehe.common.mapstruct;

import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author xiech
 * @description
 * @date 2024/1/8
 */
public interface MapstructVoBaseMapper<V, E> {

    V toVo(E entity);

    E toEntity(V vo);

    List<V> toVo(List<E> entityList);

    List<E> toEntity(List<V> voList);

}
