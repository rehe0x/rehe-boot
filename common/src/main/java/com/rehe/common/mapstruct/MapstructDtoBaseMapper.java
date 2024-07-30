package com.rehe.common.mapstruct;

import java.util.List;

/**
 * @author xiech
 * @description
 * @date 2024/1/8
 */
public interface MapstructDtoBaseMapper<V, E> {

    V toDto(E entity);

    E toEntity(V vo);

    List<V> toDto(List<E> entityList);

    List<E> toEntity(List<V> voList);

}
