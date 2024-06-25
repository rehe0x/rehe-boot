package com.rehe.common.db;

import lombok.Data;

import java.util.Properties;

/**
 * @author xiech
 * @description
 * @date 2024/1/4
 */
@Data
public class DataSourceHikariProperties {
    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private int minimumIdle;
    private int maximumPoolSize;
    private String connectionTestQuery;
    private int maxLifetime;
    private int connectionTimeout;
    private String poolName;
    private boolean autoCommit;

    private Properties dataSourceProperties = new Properties();

    public int getPrepStmtCacheSize(){
        return Integer.parseInt((String)dataSourceProperties.get("prepStmtCacheSize"));
    }

    public int getPrepStmtCacheSqlLimit(){
        return Integer.parseInt((String)dataSourceProperties.get("prepStmtCacheSqlLimit"));
    }

    public boolean getCachePrepStmts(){
        return Boolean.parseBoolean((String)dataSourceProperties.get("cachePrepStmts"));
    }

    public boolean getUseServerPrepStmts(){
        return Boolean.parseBoolean((String)dataSourceProperties.get("useServerPrepStmts"));
    }

    public boolean getRewriteBatchedStatements(){
        return Boolean.parseBoolean((String)dataSourceProperties.get("rewriteBatchedStatements"));
    }

    public boolean getCacheServerConfiguration(){
        return Boolean.parseBoolean((String)dataSourceProperties.get("cacheServerConfiguration"));
    }

    public boolean getCacheResultSetMetaData(){
        return Boolean.parseBoolean((String)dataSourceProperties.get("cacheResultSetMetaData"));
    }

    public int getMetadataCacheSize(){
        return Integer.parseInt((String)dataSourceProperties.get("metadataCacheSize"));
    }

    public boolean getMaintainTimeStats(){
        return Boolean.parseBoolean((String)dataSourceProperties.get("maintainTimeStats"));
    }

    public boolean getUseCursorFetch(){
        return Boolean.parseBoolean((String)dataSourceProperties.get("useCursorFetch"));
    }

    public int getDefaultFetchSize(){
        return Integer.parseInt((String)dataSourceProperties.get("defaultFetchSize"));
    }

    public boolean getUseCompression(){
        return Boolean.parseBoolean((String)dataSourceProperties.get("useCompression"));
    }
}
