/*
 * Copyright (c) 2002-2021 meteo@woodapiary.com
 */
package com.woodapiary.meteo.ow.app;

import com.woodapiary.meteo.ow.OwTestApplication;
import com.woodapiary.meteo.ow.config.OwConstants;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = OwTestApplication.class)
class OwApplicationTest {


    private static final Logger log = LoggerFactory.getLogger(OwApplicationTest.class);

    @Test
    void testApplication() {
        log.info("test app {}", OwConstants.module);
    }

}
