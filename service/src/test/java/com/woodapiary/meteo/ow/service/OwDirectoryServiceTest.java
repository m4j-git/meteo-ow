/*
 * Copyright (c) 2002-2021 meteo@woodapiary.com
 */
package com.woodapiary.meteo.ow.service;

import com.woodapiary.meteo.ow.OwTestApplication;
import com.woodapiary.meteo.ow.domain.OwWeather;
import com.woodapiary.meteo.ow.model.OwWeatherDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
