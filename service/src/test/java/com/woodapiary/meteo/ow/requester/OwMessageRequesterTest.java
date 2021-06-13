/*
 * Copyright (c) 2002-2021 meteo@woodapiary.com
 */
package com.woodapiary.meteo.ow.requester;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woodapiary.meteo.ow.OwTestApplication;
import com.woodapiary.meteo.ow.model.GeonameDto;
import com.woodapiary.meteo.ow.model.OwMessageDto;
import com.woodapiary.meteo.ow.repo.OwMessageRepository;
import com.woodapiary.meteo.ow.service.OwDao;
import com.woodapiary.meteo.ow.service.OwDirectoryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = OwTestApplication.class)
@Transactional
class OwMessageRequesterTest {

    private static final String testDataFile = "ow_onecall.json";
    @MockBean
    OwMessageClient client;
    @Autowired
    private OwMessageRequester requester;
    @Autowired
    private ObjectMapper jacksonMapper;
    @Autowired
    private OwDao dao;
    @Value("${meteo.test.data.path}")
    private String testDataPath;
    @Autowired
    private OwMessageRepository msgRepo;
    @Autowired
    private OwDirectoryService dir;

    @BeforeEach
    public void setUp() throws IOException {
        assertNotNull(requester);
        assertEquals(0, msgRepo.count());
        dir.saveConditionCodesToDb();
    }

    @Test
    public void testRequestProvider(@Autowired GeonameDto geoname) throws IOException {
        when(client.request(requester.getUri(geoname))).thenReturn(readJson());
        final OwMessageDto result = requester.requestProvider(geoname);
        assertNotNull(result.getCurrent().getDt());
    }

    private OwMessageDto readJson() throws IOException {
        final FileInputStream fis = new FileInputStream(testDataPath + testDataFile);
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8))) {
            return jacksonMapper.readValue(rd, OwMessageDto.class);
        }
    }

    @AfterEach
    public void tearDown() {
        dao.deleteMessages();
        dao.deleteWeatherConditionCodes();
        assertEquals(0, msgRepo.count());
    }

}
