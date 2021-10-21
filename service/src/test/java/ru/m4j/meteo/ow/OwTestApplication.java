/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;

@Profile("dev")
@SpringBootApplication
@ComponentScan(basePackages = "ru.m4j.meteo")
public class OwTestApplication {

}
