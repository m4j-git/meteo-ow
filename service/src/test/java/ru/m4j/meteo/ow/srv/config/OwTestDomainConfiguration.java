/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.srv.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@ComponentScan(basePackages = "ru.m4j.meteo.ow.domain,ru.m4j.meteo.ow.mapper")
public class OwTestDomainConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper o = new ObjectMapper();
        o.registerModule(new JavaTimeModule());
        return o;
    }

    @Bean
    public OwTestBeanSource owTestBeanSource() {
        return new OwTestBeanSource();
    }
}
