/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.app;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import lombok.extern.slf4j.Slf4j;
import ru.m4j.meteo.ow.OwTestApplication;

@SpringBootTest(classes = OwTestApplication.class)
@Slf4j
class OwApplicationTest {

    @Autowired
    private Environment env;

    @Test
    void testApplication() {
        log.info("test app {}", env.getProperty("spring.application.name"));
        assertThat(env).isNotNull();
    }

}
