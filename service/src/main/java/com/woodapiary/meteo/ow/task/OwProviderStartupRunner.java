/*
 * Copyright (c) 2002-2021 meteo@woodapiary.com
 */
package com.woodapiary.meteo.ow.task;


import com.woodapiary.meteo.ow.service.OwDirectoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

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
