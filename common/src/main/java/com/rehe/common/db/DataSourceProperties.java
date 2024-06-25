package com.rehe.common.db;

import lombok.Data;

/**
 * @author xiech
 * @description
 * @date 2024/1/4
 */

@Data
public class DataSourceProperties {
    private String driverClassName;
    private String jdbcUrl;
    private String username;
    private String password;
    private String initializationMode;
    private DataSourceHikariProperties hikari;
}
