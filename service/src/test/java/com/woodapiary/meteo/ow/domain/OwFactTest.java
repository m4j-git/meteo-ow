/*
 * Copyright (c) 2002-2021 meteo@woodapiary.com
 */
package com.woodapiary.meteo.ow.domain;

import com.woodapiary.meteo.ow.OwTestApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = OwTestApplication.class)
class OwFactTest {

    @Test
    public void testFact(@Autowired OwFact fact) {
        assertNotNull(fact);
        assertThat(fact.hashCode()).isEqualTo(0);
        assertThat(fact.toString().length()).isGreaterThan(100);
    }


}
