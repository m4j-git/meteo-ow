/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ru.m4j.meteo.ow.OwTestApplication;

@SpringBootTest(classes = OwTestApplication.class)
class OwFactTest {

    @Test
    void testFact(@Autowired OwFact fact) {
        assertNotNull(fact);
        assertThat(fact.hashCode()).isEqualTo(0);
        assertThat(fact.toString().length()).isGreaterThan(100);
    }

}
