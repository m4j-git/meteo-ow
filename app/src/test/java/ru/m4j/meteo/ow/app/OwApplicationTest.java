/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.app;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import lombok.extern.slf4j.Slf4j;
import ru.m4j.meteo.ow.srv.config.OwMysqlContainerBase;

@Slf4j
@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
class OwApplicationTest extends OwMysqlContainerBase {

    @Autowired
    private Environment env;

    @Test
    void testApplication() {
        log.info("test app {}", env.getProperty("spring.application.name"));
        assertThat(env).isNotNull();
    }

}
