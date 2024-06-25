package com.rehe.common.mapstruct;

import java.util.List;

/**
 * @author xiech
 * @description
 * @date 2024/1/8
 */
public interface MapstructBaseMapper<T, S> {

    T toTarget(S entity);


    S toSource(T entity);

    List<T> toTarget(List<S> entityList);

    List<S> toSource(List<T> entity);

}
