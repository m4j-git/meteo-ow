/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.app;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import ru.m4j.meteo.ow.OwTestApplication;
import ru.m4j.meteo.ow.config.OwConstants;


@SpringBootTest(classes = OwTestApplication.class)
class OwApplicationTest {


    private static final Logger log = LoggerFactory.getLogger(OwApplicationTest.class);

    @Test
    void testApplication() {
        log.info("test app {}", OwConstants.module);
    }

}
