/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import ru.m4j.meteo.ow.OwTestApplication;
import ru.m4j.meteo.ow.domain.OwWeather;
import ru.m4j.meteo.ow.model.OwWeatherDto;

@SpringBootTest(classes = OwTestApplication.class)
@Transactional
class OwDirectoryServiceTest {

    @Autowired
    private OwDao dao;
    @Autowired
    private OwDirectoryService requester;

    @BeforeEach
    public void setUp() {
        assertThat(requester).isNotNull();
    }

    @Test
    void testReadConditionalFromFile() throws IOException {
        List<OwWeatherDto> res = requester.readConditionCodesFromFile();
        assertThat(res.size()).isPositive();
    }

    @Test
    void testSaveConditionalToDb() throws IOException {
        Set<OwWeather> res = requester.saveConditionCodesToDb();
        assertThat(res.size()).isPositive();
        dao.deleteWeatherConditionCodes();
    }

}
