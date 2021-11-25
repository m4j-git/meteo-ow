/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import ru.m4j.meteo.ow.OwTestApplication;
import ru.m4j.meteo.ow.domain.OwAlert;
import ru.m4j.meteo.ow.domain.OwDaily;
import ru.m4j.meteo.ow.domain.OwFact;
import ru.m4j.meteo.ow.domain.OwHourly;
import ru.m4j.meteo.ow.domain.OwMessage;
import ru.m4j.meteo.ow.repo.OwAlertRepository;
import ru.m4j.meteo.ow.repo.OwDailyRepository;
import ru.m4j.meteo.ow.repo.OwFactRepository;
import ru.m4j.meteo.ow.repo.OwHourlyRepository;
import ru.m4j.meteo.ow.repo.OwMessageRepository;
import ru.m4j.meteo.ow.repo.OwWeatherRepository;

@SpringBootTest(classes = OwTestApplication.class)
@Transactional
class OwDaoTest {

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
        assertThat(0).isEqualTo(alertRepo.count());
        assertThat(55).isEqualTo(weatherRepo.count());
        assertThat(0).isEqualTo(dailyRepo.count());
        assertThat(0).isEqualTo(hourlyRepo.count());
        assertThat(0).isEqualTo(factRepo.count());
        assertThat(0).isEqualTo(msgRepo.count());
    }

    @Test
    void testCreateMessageSkinny(@Qualifier("message_skinny") OwMessage mes) {
        final OwMessage ent = dao.saveMessage(mes, geonameId);
        assertNotNull(ent.getMessageId());
        assertThat(1).isEqualTo(msgRepo.count());
    }

    @Test
    void testCreateMessage(@Qualifier("message") OwMessage mes) throws IOException {
        dir.saveConditionCodesToDb();
        mes = dao.saveMessage(mes, geonameId);
        assertThat(1).isEqualTo(factRepo.count());
        assertNotNull(mes.getFact().getFactId());
        assertThat(2).isEqualTo(alertRepo.count());
        OwAlert[] alerts = new OwAlert[mes.getAlerts().size()];
        alerts = mes.getAlerts().toArray(alerts);
        assertNotNull(alerts[0].getAlertId());
        assertThat(1).isEqualTo(dailyRepo.count());
        OwDaily[] dailies = new OwDaily[mes.getDailies().size()];
        dailies = mes.getDailies().toArray(dailies);
        assertNotNull(dailies[0].getDailyId());
        assertThat(2).isEqualTo(hourlyRepo.count());
        OwHourly[] hourlies = new OwHourly[mes.getHourlies().size()];
        hourlies = mes.getHourlies().toArray(hourlies);
        assertNotNull(hourlies[0].getHourlyId());
    }

    @Test
    void testFindLastMessage(@Qualifier("message") OwMessage mes) {
        final OwMessage ent = dao.saveMessage(mes, geonameId);
        final OwMessage ent2 = dao.findLastMessage(geonameId);
        assertThat(ent).isEqualTo(ent2);
    }

    @Test
    void testFindFacts(@Qualifier("message") OwMessage mes) {
        final OwMessage ent = dao.saveMessage(mes, geonameId);
        final List<OwFact> fact2List = dao.findFacts(geonameId, LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneId.systemDefault()),
            LocalDateTime.ofInstant(Instant.ofEpochSecond(Integer.MAX_VALUE), ZoneId.systemDefault()));
        assertThat(1).isEqualTo(fact2List.size());
        assertThat(ent.getFact()).isEqualTo(fact2List.get(0));
    }

    @Test
    void testFindFactsViaSpecification(@Qualifier("message") OwMessage mes) {
        final OwMessage ent = dao.saveMessage(mes, geonameId);
        final List<OwFact> fact2List = dao.findFactsViaSpecification(geonameId,
            LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneId.systemDefault()),
            LocalDateTime.ofInstant(Instant.ofEpochSecond(Integer.MAX_VALUE), ZoneId.systemDefault()));
        assertThat(1).isEqualTo(fact2List.size());
        assertThat(ent.getFact()).isEqualTo(fact2List.get(0));
        assertNotNull(fact2List.get(0).getFactId());
    }

    @Test
    void testFindMessages(@Qualifier("message") OwMessage mes) {
        final OwMessage ent = dao.saveMessage(mes, geonameId);
        final List<OwMessage> ent2List = dao.findMessages(geonameId, LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneId.systemDefault()),
            LocalDateTime.ofInstant(Instant.ofEpochSecond(Integer.MAX_VALUE), ZoneId.systemDefault()));
        assertThat(1).isEqualTo(ent2List.size());
        assertThat(ent).isEqualTo(ent2List.get(0));
    }

    @Test
    void testFindMessagesViaSpecification(@Qualifier("message") OwMessage mes) {
        final OwMessage ent = dao.saveMessage(mes, geonameId);
        assertThat(1).isEqualTo(msgRepo.count());
        final List<OwMessage> ent2List = dao.findMessagesViaSpecification(geonameId,
            LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneId.systemDefault()),
            LocalDateTime.ofInstant(Instant.ofEpochSecond(Integer.MAX_VALUE), ZoneId.systemDefault()));
        assertThat(1).isEqualTo(ent2List.size());
        assertThat(ent).isEqualTo(ent2List.get(0));
    }

    @Test
    void testFindMessageByUuid(@Qualifier("message") OwMessage ent1) {
        UUID uuid = UUID.randomUUID();
        ent1.setMessageUuid(uuid);
        ent1 = dao.saveMessage(ent1, geonameId);
        final OwMessage ent2 = dao.findMessageByUuid(uuid);
        assertThat(ent1).isEqualTo(ent2);
    }

    @AfterEach
    public void tearDown() {
        dao.deleteAllMessages();
        dao.deleteWeatherConditionCodes();
        assertThat(0).isEqualTo(alertRepo.count());
        assertThat(0).isEqualTo(weatherRepo.count());
        assertThat(0).isEqualTo(dailyRepo.count());
        assertThat(0).isEqualTo(hourlyRepo.count());
        assertThat(0).isEqualTo(factRepo.count());
        assertThat(0).isEqualTo(msgRepo.count());
    }

}
