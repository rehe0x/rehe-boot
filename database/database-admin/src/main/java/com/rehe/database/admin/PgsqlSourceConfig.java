package com.rehe.database.admin;

import com.rehe.common.YamlPropertySourceFactory;
import com.rehe.common.db.DataSourceProperties;
import com.rehe.common.db.DynamicDataSource;
import com.rehe.common.db.DynamicDataSourceEnum;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.*;

/**
 * @author xiech
 * @description
 * @date 2024/1/4
 */
@Configuration
@MapperScan(basePackages = {"com.rehe.auth.admin.mapper","com.rehe.modules.admin.**.mapper"}, sqlSessionTemplateRef  = "pgsqlSqlTemplate")
@PropertySource(value = "classpath:config/database-admin-${spring.profiles.active}.yaml",factory = YamlPropertySourceFactory.class)
public class PgsqlSourceConfig {

    /**
     * 主配置类
     */
    @Bean("pgsqlMasterDataSourceProperties")
    @ConfigurationProperties(prefix = "database.pgsql.master")
    public DataSourceProperties masterDataSourceProperties(){
        return new DataSourceProperties();
    }

    /**
     * 从配置类
     */
    @Bean("pgsqlSlaveDataSourceProperties")
    @ConditionalOnProperty(prefix = "database.pgsql", name = "slave.jdbc-url")
    @ConfigurationProperties(prefix = "database.pgsql.slave")
    public DataSourceProperties slaveDataSourceProperties(){
        return new DataSourceProperties();
    }

    /**
     * 主数据源
     */
    @Bean("pgsqlMasterDataSource")
    public DataSource masterDataSource(@Qualifier("pgsqlMasterDataSourceProperties") DataSourceProperties dataSourceProperties){
        return createHikariDataSource(dataSourceProperties);
    }

    /**
     * 从数据源
     */
    @Bean("pgsqlSlaveDataSource")
    @ConditionalOnBean(name = "pgsqlSlaveDataSourceProperties")
    public DataSource slaveDataSource(@Qualifier("pgsqlSlaveDataSourceProperties") DataSourceProperties dataSourceProperties){
        return createHikariDataSource(dataSourceProperties);
    }

    /**
     * 创建数据源
     */
    public HikariDataSource createHikariDataSource(DataSourceProperties dataSourceProperties){
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(dataSourceProperties.getDriverClassName());
        hikariConfig.setJdbcUrl(dataSourceProperties.getJdbcUrl());
        hikariConfig.setUsername(dataSourceProperties.getUsername());
        hikariConfig.setPassword(dataSourceProperties.getPassword());
        hikariConfig.setPoolName(dataSourceProperties.getHikari().getPoolName());
        hikariConfig.setMinimumIdle(dataSourceProperties.getHikari().getMinimumIdle());
        hikariConfig.setMaximumPoolSize(dataSourceProperties.getHikari().getMaximumPoolSize());
        hikariConfig.setConnectionTestQuery(dataSourceProperties.getHikari().getConnectionTestQuery());
        hikariConfig.setMaxLifetime(dataSourceProperties.getHikari().getMaxLifetime());
        hikariConfig.setConnectionTimeout(dataSourceProperties.getHikari().getConnectionTimeout());
        hikariConfig.setAutoCommit(dataSourceProperties.getHikari().isAutoCommit());
        hikariConfig.setDataSourceProperties(dataSourceProperties.getHikari().getDataSourceProperties());
        return new HikariDataSource(hikariConfig);
    }

    /**
     * 主从动态配置
     */
    @Bean(name = "pgsqlDynamicDb")
    public DynamicDataSource dynamicDb(@Qualifier("pgsqlMasterDataSource") DataSource masterDataSource,
                                       @Autowired(required = false) @Qualifier("pgsqlSlaveDataSource") DataSource slaveDataSource) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DynamicDataSourceEnum.MASTER.getDataSourceName(), masterDataSource);
        if (slaveDataSource != null) {
            targetDataSources.put(DynamicDataSourceEnum.SLAVE.getDataSourceName(), slaveDataSource);
        }
        dynamicDataSource.setTargetDataSources(targetDataSources);
        dynamicDataSource.setDefaultTargetDataSource(masterDataSource);
        return dynamicDataSource;
    }


    @Bean(name = "pgsqlSessionFactory")
    public SqlSessionFactory sessionFactory(@Qualifier("pgsqlDynamicDb") DataSource dynamicDataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setMapperLocations(resolveMapperLocations());
        bean.setDataSource(dynamicDataSource);
        Objects.requireNonNull(bean.getObject()).getConfiguration().setMapUnderscoreToCamelCase(true);
        return bean.getObject();
    }

    public Resource[] resolveMapperLocations() throws IOException{
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        List<String> mapperLocations = new ArrayList<>();
        mapperLocations.add("classpath*:admin_mapper/**/*Mapper.xml");
        mapperLocations.add("classpath*:auth_mapper/*Mapper.xml");
        List<Resource> resources = new ArrayList<>();

        for (String mapperLocation : mapperLocations) {
            Resource[] mappers = resourceResolver.getResources(mapperLocation);
            resources.addAll(Arrays.asList(mappers));
        }

        return resources.toArray(new Resource[0]);
    }

    @Bean(name = "pgsqlSqlTemplate")
    public SqlSessionTemplate sqlTemplate(@Qualifier("pgsqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "pgsqlDataSourceTx")
    @Primary
    public DataSourceTransactionManager dataSourceTx(@Qualifier("pgsqlDynamicDb") DataSource dynamicDataSource) {
        return new DataSourceTransactionManager(dynamicDataSource);
    }

}
