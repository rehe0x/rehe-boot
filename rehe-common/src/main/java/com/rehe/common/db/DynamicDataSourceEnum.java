package com.rehe.common.db;

import lombok.Getter;

/**
 * @author xiech
 * @description
 * @date 2024/1/4
 */
@Getter
public enum DynamicDataSourceEnum {
    MASTER("master"),
    SLAVE("slave");
    private final String dataSourceName;
    DynamicDataSourceEnum(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }
}
