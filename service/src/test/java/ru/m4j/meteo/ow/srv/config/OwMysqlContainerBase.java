/*
 * Copyright (c) 2002-2022 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.srv.config;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

public abstract class OwMysqlContainerBase {

    static MySQLContainer<?> mySqlContainer = new MySQLContainer<>("mysql:8.0")
        .withReuse(true)
        .withDatabaseName("meteo-ow")
        .withUsername("test")
        .withPassword("test");

    static {
        mySqlContainer.start();
    }

    @DynamicPropertySource
    static void mysqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySqlContainer::getJdbcUrl);
    }

}
