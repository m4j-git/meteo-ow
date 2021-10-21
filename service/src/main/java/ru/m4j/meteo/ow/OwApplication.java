/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Profile;

@Profile("!dev")
@SpringBootApplication
@EntityScan(basePackages = {"ru.m4j.meteo.*.domain"})
public class OwApplication extends SpringBootServletInitializer {
    private static final Logger log = LoggerFactory.getLogger(OwApplication.class);

    public static void main(final String[] args) {
        SpringApplication.run(OwApplication.class, args);
    }

}
