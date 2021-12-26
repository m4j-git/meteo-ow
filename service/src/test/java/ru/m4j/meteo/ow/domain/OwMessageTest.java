/*
 * Copyright (c) 2002-2022 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import lombok.extern.slf4j.Slf4j;
import ru.m4j.meteo.ow.srv.config.OwTestBeanFactory;
import ru.m4j.meteo.ow.srv.config.OwTestDomainConfiguration;

@Slf4j
@SpringBootTest(classes = { OwTestDomainConfiguration.class })
@Import(OwTestBeanFactory.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
class OwMessageTest {

    @Test
    void entity_test(@Qualifier("message_skinny") OwMessage mes) {
        assertThat(mes).isNotNull();
        assertThat(mes.getMessageUuid()).isNotNull();
        log.info("message: " + mes);
    }

    @Test
    void agregate_test(@Qualifier("message") OwMessage mes) {
        assertThat(mes).isNotNull();
        assertThat(mes.getFact()).isNotNull();
        assertThat(mes.getDailies()).isNotNull();
    }

}
