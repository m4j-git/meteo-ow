/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.m4j.meteo.ow.OwTestApplication;
import ru.m4j.meteo.ow.domain.OwWeather;
import ru.m4j.meteo.ow.model.OwWeatherDto;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(classes = OwTestApplication.class)
@Transactional
class OwDirectoryServiceTest {

    @Autowired
    private OwDao dao;
    @Autowired
    private OwDirectoryService requester;

    @BeforeEach
    public void setUp() {
        assertNotNull(requester);
    }

    @Test
    public void testReadConditionalFromFile() throws IOException {
        final List<OwWeatherDto> res = requester.readConditionCodesFromFile();
        assertTrue(res.size() > 0);
    }

    @Test
    public void testSaveConditionalToDb() throws IOException {
        final Set<OwWeather> res = requester.saveConditionCodesToDb();
        assertTrue(res.size() > 0);
        dao.deleteWeatherConditionCodes();
    }

}
