/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.requester;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.m4j.meteo.ow.OwTestApplication;
import ru.m4j.meteo.ow.model.LocationDto;
import ru.m4j.meteo.ow.model.OwMessageDto;
import ru.m4j.meteo.ow.repo.OwMessageRepository;
import ru.m4j.meteo.ow.service.OwDao;
import ru.m4j.meteo.ow.service.OwDirectoryService;
import ru.m4j.meteo.share.app.GlobalConstants;

@SpringBootTest(classes = OwTestApplication.class)
@Transactional
class OwMessageRequesterTest {

    private static final String TEST_DATA_FILE = "ow_onecall.json";
    @MockBean
    OwMessageClient client;
    @Autowired
    private OwMessageRequester requester;
    @Autowired
    private ObjectMapper jacksonMapper;
    @Autowired
    private OwDao dao;
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
    void testRequestProvider(@Autowired LocationDto geoname) throws IOException {
        when(client.request(requester.getUri(geoname))).thenReturn(readJson());
        OwMessageDto result = requester.requestProvider(geoname);
        assertNotNull(result.getCurrent().getDt());
    }

    private OwMessageDto readJson() throws IOException {
        FileInputStream fis = new FileInputStream(GlobalConstants.TEST_DATA_PATH + TEST_DATA_FILE);
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
