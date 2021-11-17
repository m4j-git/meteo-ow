/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.m4j.meteo.ow.OwTestApplication;
import ru.m4j.meteo.ow.app.OwTestConstants;
import ru.m4j.meteo.ow.model.OwCurrentDto;
import ru.m4j.meteo.ow.model.OwMessageDto;
import ru.m4j.meteo.ow.repo.OwAlertRepository;
import ru.m4j.meteo.ow.repo.OwDailyRepository;
import ru.m4j.meteo.ow.repo.OwFactRepository;
import ru.m4j.meteo.ow.repo.OwHourlyRepository;
import ru.m4j.meteo.ow.repo.OwMessageRepository;
import ru.m4j.meteo.ow.repo.OwWeatherRepository;

@SpringBootTest(classes = OwTestApplication.class)
@Transactional
class OwMessageServiceTest {

    private static final String testDataFile = "ow_onecall.json";
    private final Integer geonameId = 1;
    private final String messageUuid = "11111111-1111-1111-1111-111111111111";

    @Autowired
    private OwMessageService service;
    @Autowired
    private OwDao dao;
    @Autowired
    private OwDirectoryService dir;
    @Autowired
    private ObjectMapper jacksonMapper;
    @Autowired
    private OwMessageRepository msgRepo;
    @Autowired
    private OwFactRepository factRepo;
    @Autowired
    private OwWeatherRepository weatherRepo;
    @Autowired
    private OwAlertRepository alertRepo;
    @Autowired
    private OwDailyRepository dailyRepo;
    @Autowired
    private OwHourlyRepository hourlyRepo;

    @BeforeEach
    public void setUp() throws IOException {
        assertNotNull(service);
        assertEquals(0, alertRepo.count());
        assertEquals(0, weatherRepo.count());
        assertEquals(0, dailyRepo.count());
        assertEquals(0, hourlyRepo.count());
        assertEquals(0, factRepo.count());
        assertEquals(0, msgRepo.count());
        final FileInputStream fis = new FileInputStream(OwTestConstants.testDataPath + testDataFile);
        OwMessageDto dto;
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8))) {
            dto = jacksonMapper.readValue(rd, OwMessageDto.class);
        }
        assertNotNull(dto.getCurrent().getDt());
        dir.saveConditionCodesToDb();
        dto.setMessageUuid(UUID.fromString(messageUuid));
        service.saveMessageToDb(dto, geonameId);
        assertEquals(2, alertRepo.count());
        assertEquals(55, weatherRepo.count());
        assertEquals(1, dailyRepo.count());
        assertEquals(2, hourlyRepo.count());
        assertEquals(1, factRepo.count());
        assertEquals(1, msgRepo.count());
    }

    @Test
    void testGetLastMessage() {
        final OwMessageDto dto = service.getLastMessage(geonameId);
        assertNotNull(dto);
        assertNotNull(dto.getCreatedOn());
        assertNotNull(dto.getMessageUuid());
    }

    @Test
    void testGetFacts() {
        final List<OwCurrentDto> fact2List = service.getFacts(geonameId, null, null);
        assertEquals(1, fact2List.size());
        assertNotNull(fact2List.get(0));
    }

    @Test
    void testGetMessages() {
        final List<OwMessageDto> ent2List = service.getMessages(geonameId, null, null);
        assertEquals(1, ent2List.size());
        assertNotNull(ent2List.get(0));
    }

    @Test
    void testGetMessage() {
        final OwMessageDto dto = service.getMessage(messageUuid);
        assertNotNull(dto);
    }

    @AfterEach
    public void tearDown() {
        dao.deleteAllMessages();
        dao.deleteWeatherConditionCodes();
        assertEquals(0, alertRepo.count());
        assertEquals(0, weatherRepo.count());
        assertEquals(0, dailyRepo.count());
        assertEquals(0, hourlyRepo.count());
        assertEquals(0, factRepo.count());
        assertEquals(0, msgRepo.count());
    }
}
