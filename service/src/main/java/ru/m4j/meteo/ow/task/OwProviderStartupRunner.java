/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.task;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ru.m4j.meteo.ow.service.OwDirectoryService;

@Component
@ConditionalOnProperty(name = "meteo.scheduling.enabled", havingValue = "true")
public class OwProviderStartupRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(OwProviderStartupRunner.class);

    private final OwDirectoryService dir;

    @Value("${spring.application.name}")
    private String appName;

    public OwProviderStartupRunner(OwDirectoryService dir) {
        this.dir = dir;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("=== {} startup runner run ===", appName);
        dir.startUp();
    }

}
