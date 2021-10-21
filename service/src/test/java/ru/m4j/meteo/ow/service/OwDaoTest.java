/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.service;

import ru.m4j.meteo.ow.OwTestApplication;
import ru.m4j.meteo.ow.domain.*;
import ru.m4j.meteo.ow.repo.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = OwTestApplication.class)
@Transactional
public class OwDaoTest {

    private final Integer geonameId = 2;
    @Autowired
    private OwDao dao;
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
    @Autowired
    private OwDirectoryService dir;

    @BeforeEach
    public void setUp() throws IOException {
        assertThat(dao).isNotNull();
        dir.saveConditionCodesToDb();
        assertEquals(0, alertRepo.count());
        assertEquals(55, weatherRepo.count());
        assertEquals(0, dailyRepo.count());
        assertEquals(0, hourlyRepo.count());
        assertEquals(0, factRepo.count());
        assertEquals(0, msgRepo.count());
    }

    @Test
    public void testCreateMessageSkinny(@Qualifier("message_skinny") OwMessage mes) {
        final OwMessage ent = dao.saveMessage(mes, geonameId);
        assertNotNull(ent.getMessageId());
        assertEquals(1, msgRepo.count());
    }

    @Test
    public void testCreateMessage(@Qualifier("message") OwMessage mes) throws IOException {
        dir.saveConditionCodesToDb();
        mes = dao.saveMessage(mes, geonameId);
        assertEquals(1, factRepo.count());
        assertNotNull(mes.getFact().getFactId());
        assertEquals(2, alertRepo.count());
        OwAlert[] alerts = new OwAlert[mes.getAlerts().size()];
        alerts = mes.getAlerts().toArray(alerts);
        assertNotNull(alerts[0].getAlertId());
        assertEquals(1, dailyRepo.count());
        OwDaily[] dailies = new OwDaily[mes.getDailies().size()];
        dailies = mes.getDailies().toArray(dailies);
        assertNotNull(dailies[0].getDailyId());
        assertEquals(2, hourlyRepo.count());
        OwHourly[] hourlies = new OwHourly[mes.getHourlies().size()];
        hourlies = mes.getHourlies().toArray(hourlies);
        assertNotNull(hourlies[0].getHourlyId());
    }


    @Test
    public void testFindLastMessage(@Qualifier("message") OwMessage mes) {
        final OwMessage ent = dao.saveMessage(mes, geonameId);
        final OwMessage ent2 = dao.findLastMessage(geonameId);
        assertEquals(ent, ent2);
    }

    @Test
    public void testFindFacts(@Qualifier("message") OwMessage mes) {
        final OwMessage ent = dao.saveMessage(mes, geonameId);
        final List<OwFact> fact2List = dao.findFacts(geonameId,
                LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneId.systemDefault()),
                LocalDateTime.ofInstant(Instant.ofEpochSecond(Integer.MAX_VALUE), ZoneId.systemDefault()));
        assertEquals(1, fact2List.size());
        assertEquals(ent.getFact(), fact2List.get(0));
    }

    @Test
    public void testFindFactsViaSpecification(@Qualifier("message") OwMessage mes) {
        final OwMessage ent = dao.saveMessage(mes, geonameId);
        final List<OwFact> fact2List = dao.findFactsViaSpecification(geonameId,
                LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneId.systemDefault()),
                LocalDateTime.ofInstant(Instant.ofEpochSecond(Integer.MAX_VALUE), ZoneId.systemDefault()));
        assertEquals(1, fact2List.size());
        assertEquals(ent.getFact(), fact2List.get(0));
        assertNotNull(fact2List.get(0).getFactId());
    }

    @Test
    public void testFindMessages(@Qualifier("message") OwMessage mes) {
        final OwMessage ent = dao.saveMessage(mes, geonameId);
        final List<OwMessage> ent2List = dao.findMessages(geonameId,
                LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneId.systemDefault()),
                LocalDateTime.ofInstant(Instant.ofEpochSecond(Integer.MAX_VALUE), ZoneId.systemDefault()));
        assertEquals(1, ent2List.size());
        assertEquals(ent, ent2List.get(0));
    }

    @Test
    public void testFindMessagesViaSpecification(@Qualifier("message") OwMessage mes) {
        final OwMessage ent = dao.saveMessage(mes, geonameId);
        assertEquals(1, msgRepo.count());
        final List<OwMessage> ent2List = dao.findMessagesViaSpecification(geonameId,
                LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneId.systemDefault()),
                LocalDateTime.ofInstant(Instant.ofEpochSecond(Integer.MAX_VALUE), ZoneId.systemDefault()));
        assertEquals(1, ent2List.size());
        assertEquals(ent, ent2List.get(0));
    }

    @Test
    public void testFindMessageByUuid(@Qualifier("message") OwMessage ent1) {
        UUID uuid = UUID.randomUUID();
        ent1.setMessageUuid(uuid);
        ent1 = dao.saveMessage(ent1, geonameId);
        final OwMessage ent2 = dao.findMessageByUuid(uuid);
        assertEquals(ent1, ent2);
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
