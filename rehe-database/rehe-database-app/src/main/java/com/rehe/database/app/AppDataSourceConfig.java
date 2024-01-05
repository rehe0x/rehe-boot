package com.rehe.database.app;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.rehe.common.YamlPropertySourceFactory;
import com.rehe.common.db.DataSourceProperties;
import com.rehe.common.db.DynamicDataSource;
import com.rehe.common.db.DynamicDataSourceEnum;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author xiech
 * @description
 * @date 2024/1/4
 */
@Configuration
@MapperScan(basePackages = {"com.rehe.database.app.mapper","com.rehe.auth.admin.test.mapper"}, sqlSessionTemplateRef  = "appSqlTemplate")
@PropertySource(value = "classpath:config/database-app-${spring.profiles.active}.yaml",factory = YamlPropertySourceFactory.class)
public class AppDataSourceConfig {
    /**
     * 主配置类
     */
    @Bean("appMasterDataSourceProperties")
    @ConfigurationProperties(prefix = "database.app.master")
    public DataSourceProperties masterDataSourceProperties(){
        return new DataSourceProperties();
    }

    /**
     * 从配置类
     */
    @Bean("appSlaveDataSourceProperties")
    @ConditionalOnProperty(prefix = "database.app", name = "slave.jdbc-url")
    @ConfigurationProperties(prefix = "database.app.slave")
    public DataSourceProperties slaveDataSourceProperties(){
        return new DataSourceProperties();
    }

    /**
     * 主数据源
     */
    @Bean("appMasterDataSource")
    public DataSource masterDataSource(@Qualifier("appMasterDataSourceProperties") DataSourceProperties dataSourceProperties){
        return createHikariDataSource(dataSourceProperties);
    }

    /**
     * 从数据源
     */
    @Bean("appSlaveDataSource")
    @ConditionalOnBean(name = "appSlaveDataSourceProperties")
    public DataSource slaveDataSource(@Qualifier("appSlaveDataSourceProperties") DataSourceProperties dataSourceProperties){
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
    @Bean(name = "appDynamicDb")
    public DynamicDataSource dynamicDb(@Qualifier("appMasterDataSource") DataSource masterDataSource,
                                       @Autowired(required = false) @Qualifier("appSlaveDataSource") DataSource slaveDataSource) {
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


    @Bean(name = "appSessionFactory")
    public SqlSessionFactory sessionFactory(@Qualifier("appDynamicDb") DataSource dynamicDataSource) throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*Mapper.xml"));
        bean.setDataSource(dynamicDataSource);
        Objects.requireNonNull(bean.getObject()).getConfiguration().setMapUnderscoreToCamelCase(true);
        return bean.getObject();
    }


    @Bean(name = "appSqlTemplate")
    public SqlSessionTemplate sqlTemplate(@Qualifier("appSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "appDataSourceTx")
    public DataSourceTransactionManager dataSourceTx(@Qualifier("appDynamicDb") DataSource dynamicDataSource) {
        return new DataSourceTransactionManager(dynamicDataSource);
    }

}
