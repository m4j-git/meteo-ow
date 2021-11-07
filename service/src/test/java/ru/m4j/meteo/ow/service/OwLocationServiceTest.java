/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import ru.m4j.meteo.ow.OwTestApplication;

@SpringBootTest(classes = OwTestApplication.class)
public class OwLocationServiceTest {

    @Autowired
    OwLocationService service;

    @Test
    public void test01() throws JsonGenerationException, JsonMappingException, IOException {
        assertThat(service.requestGeonames().size() > 0);
    }

}
