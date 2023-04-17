package com.pkh.configuraton.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@EnableAutoConfiguration
@MapperScan(basePackages = "com.pkh.dao.mapper.admindb",sqlSessionTemplateRef = "admindbSessionTemplate")
public class DataSourceAdmindbConfiguration {

    /**
     * 读取配置文件中，以admin.datasource为前缀的配置项
     * @return
     */
    @Bean
    @ConfigurationProperties("admin.datasource")
    public HikariConfig admindbConfig() {
        HikariConfig hikariConfig = new HikariConfig();
        return hikariConfig;
    }

    /**
     * 根据配置类，获取DataSource
     *
     * @return
     */
    @Bean("admindbDataSource")
    public DataSource getAdmindbDataSource() {
        return new HikariDataSource(admindbConfig());
    }

    /**
     * 根据DataSource获取SqlSessionFactory
     *
     * @param dataSource 数据源
     * @return
     * @throws Exception
     */
    @Bean("admindbSessionFactory")
    public SqlSessionFactory admindbSessionFactory(@Qualifier("admindbDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/admindb/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 根据SqlSessionFactory 获取 SqlSessionTemplate
     *
     * @param sqlSessionFactory sql会话工厂
     * @return
     */
    @Bean("admindbSessionTemplate")
    public SqlSessionTemplate admindbSessionTemplate(@Qualifier("admindbSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
