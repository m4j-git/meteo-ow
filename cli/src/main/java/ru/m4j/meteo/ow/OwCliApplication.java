/*
 * Copyright (c) 2002-2022 meteo@m4j.ru
 */
package ru.m4j.meteo.ow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

import ru.m4j.meteo.ow.model.OwMessageDto;
import ru.m4j.meteo.ow.service.OwMessageService;

@SpringBootApplication
@ComponentScan(basePackages = { "ru.m4j.meteo.share.app", "ru.m4j.meteo.ow" })
public class OwCliApplication implements CommandLineRunner {

    @Autowired
    OwMessageService service;

    public static void main(final String[] args) {
        new SpringApplicationBuilder(OwCliApplication.class)
            .web(WebApplicationType.NONE)
            .run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("hook");
        OwMessageDto dto = service.getLastMessage(1);
        System.out.println(dto);
    }

}
