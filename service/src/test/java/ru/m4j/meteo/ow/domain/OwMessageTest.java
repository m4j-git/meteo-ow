/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.domain;

import ru.m4j.meteo.ow.OwTestApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = OwTestApplication.class)
class OwMessageTest {

    @Test
    public void testMessageSkinny(@Qualifier("message_skinny") OwMessage mes) {
        assertNotNull(mes);
        assertNotNull(mes.getMessageUuid());
        assertThat(mes.hashCode()).isEqualTo(0);
        assertThat(mes.toString().length()).isGreaterThan(100);
    }

    @Test
    public void testMessage(@Qualifier("message") OwMessage mes) {
        assertNotNull(mes);
        assertNotNull(mes.getFact());
        assertNotNull(mes.getDailies());
    }

}
