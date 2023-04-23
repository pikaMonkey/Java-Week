package com.pkh.configuraton.datasource;

import com.pkh.commons.datasource.DynamicDataSource;
import com.pkh.commons.datasource.DynamicDataSourceContextHolder;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfiguration {
    @Bean
    @ConfigurationProperties(prefix = "pkhdb.datasource")
    public HikariConfig pkhdbConfig() {
        return new HikariConfig();
    }

    @Bean
    @ConfigurationProperties(prefix = "admin.datasource")
    public HikariConfig admindbConfig() {
        return new HikariConfig();
    }

    @Bean("dataSource")
    public DataSource dataSource() {
        // 将多数据源存储在Map中
        Map<Object, Object> dataSourceMap = new HashMap<>();
        DataSource pkhDs = new HikariDataSource(pkhdbConfig());
        DataSource adminDs = new HikariDataSource(admindbConfig());
        dataSourceMap.put("pkhdb", pkhDs);
        dataSourceMap.put("admin", adminDs);

        // 设置动态数据源
        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(dataSourceMap);

        // 记录多数据源ID
        DynamicDataSourceContextHolder.dataSourceIds.add("pkhdb");
        DynamicDataSourceContextHolder.dataSourceIds.add("admin");

        //设置默认的数据源
        dataSource.setDefaultTargetDataSource(pkhDs);
        return dataSource;
    }
}
