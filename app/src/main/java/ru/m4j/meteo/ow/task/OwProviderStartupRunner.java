/*
 * Copyright (c) 2002-2022 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.task;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import ru.m4j.meteo.ow.service.OwDirectoryService;

@Slf4j
@Component
@ConditionalOnProperty(name = "meteo.scheduling.enabled", havingValue = "true", matchIfMissing = false)
public class OwProviderStartupRunner implements CommandLineRunner {

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
