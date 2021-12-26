/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.srv.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.zaxxer.hikari.HikariDataSource;

@Profile("dev")
@Configuration
public class OwDataSourceMysqlTcConfiguration {

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setJdbcUrl(jdbcUrl +
            "?&serverTimezone=Europe/Moscow&useUnicode=true&characterEncoding=utf8&useSSL=false&requireSSL=false&allowPublicKeyRetrieval=true");
        dataSource.setUsername("test");
        dataSource.setPassword("test");
        dataSource.setMaximumPoolSize(3);
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        Properties jpaProperties = new Properties();
        entityManagerFactoryBean.setJpaProperties(jpaProperties);
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        entityManagerFactoryBean.setPackagesToScan("ru.m4j.meteo.**.domain");
        vendorAdapter.setGenerateDdl(false);
        vendorAdapter.setShowSql(false);

        jpaProperties.put(AvailableSettings.DIALECT, "org.hibernate.dialect.MySQL55Dialect");
        jpaProperties.put(AvailableSettings.HBM2DDL_AUTO, "validate");
        jpaProperties.put(AvailableSettings.USE_NEW_ID_GENERATOR_MAPPINGS, "false");

        return entityManagerFactoryBean;
    }

}
