/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import ru.m4j.meteo.ow.OwTestApplication;

@SpringBootTest(classes = OwTestApplication.class)
class OwMessageTest {

    @Test
    void testMessageSkinny(@Qualifier("message_skinny") OwMessage mes) {
        assertNotNull(mes);
        assertNotNull(mes.getMessageUuid());
        assertThat(mes.hashCode()).isZero();
        assertThat(mes.toString().length()).isGreaterThan(100);
    }

    @Test
    void testMessage(@Qualifier("message") OwMessage mes) {
        assertNotNull(mes);
        assertNotNull(mes.getFact());
        assertNotNull(mes.getDailies());
    }

}
