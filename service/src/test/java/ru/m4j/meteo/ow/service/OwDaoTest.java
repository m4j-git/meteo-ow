/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.service;

import static org.assertj.core.api.Assertions.assertThat;

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

import ru.m4j.meteo.ow.domain.OwAlert;
import ru.m4j.meteo.ow.domain.OwDaily;
import ru.m4j.meteo.ow.domain.OwFact;
import ru.m4j.meteo.ow.domain.OwHourly;
import ru.m4j.meteo.ow.domain.OwMessage;
import ru.m4j.meteo.ow.domain.OwWeather;

public abstract class OwDaoTest {

    private final Integer geonameId = 2;

    private OwDao dao;

    public OwDaoTest(OwDao dao) {
        this.dao = dao;
    }

    @Autowired
    private OwDirectoryService dir;

    @BeforeEach
    void setUp() throws IOException {
        assertThat(dao).isNotNull();
        dir.saveConditionCodesToDb();
        assertThat(dao.count(OwAlert.class)).isZero();
        assertThat(dao.count(OwWeather.class)).isEqualTo(55);
        assertThat(dao.count(OwDaily.class)).isZero();
        assertThat(dao.count(OwHourly.class)).isZero();
        assertThat(dao.count(OwFact.class)).isZero();
        assertThat(dao.count(OwMessage.class)).isZero();
    }

    @Test
    void testCreateMessageSkinny(@Qualifier("message_skinny") OwMessage mes) {
        OwMessage ent = dao.saveMessage(mes, geonameId);
        assertThat(ent.getMessageId()).isNotNull();
        assertThat(dao.count(OwMessage.class)).isEqualTo(1);
    }

    @Test
    void testCreateMessage(@Qualifier("message") OwMessage mes) throws IOException {
        dir.saveConditionCodesToDb();
        mes = dao.saveMessage(mes, geonameId);
        assertThat(dao.count(OwFact.class)).isEqualTo(1);
        assertThat(mes.getFact().getFactId()).isNotNull();
        assertThat(dao.count(OwAlert.class)).isEqualTo(2);
        OwAlert[] alerts = new OwAlert[mes.getAlerts().size()];
        alerts = mes.getAlerts().toArray(alerts);
        assertThat(alerts[0].getAlertId()).isNotNull();
        assertThat(dao.count(OwDaily.class)).isEqualTo(1);
        OwDaily[] dailies = new OwDaily[mes.getDailies().size()];
        dailies = mes.getDailies().toArray(dailies);
        assertThat(dailies[0].getDailyId()).isNotNull();
        assertThat(dao.count(OwHourly.class)).isEqualTo(2);
        OwHourly[] hourlies = new OwHourly[mes.getHourlies().size()];
        hourlies = mes.getHourlies().toArray(hourlies);
        assertThat(hourlies[0].getHourlyId()).isNotNull();
    }

    @Test
    void testFindLastMessage(@Qualifier("message") OwMessage mes) {
        OwMessage ent = dao.saveMessage(mes, geonameId);
        OwMessage ent2 = dao.findLastMessage(geonameId).get();
        assertThat(ent).isEqualTo(ent2);
    }

    @Test
    void testFindFacts(@Qualifier("message") OwMessage mes) {
        OwMessage ent = dao.saveMessage(mes, geonameId);
        List<OwFact> fact2List = dao.findFacts(geonameId, LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneId.systemDefault()),
            LocalDateTime.ofInstant(Instant.ofEpochSecond(Integer.MAX_VALUE), ZoneId.systemDefault()));
        assertThat(fact2List.size()).isEqualTo(1);
        assertThat(ent.getFact()).isEqualTo(fact2List.get(0));
        assertThat(fact2List.get(0).getFactId()).isNotNull();
    }

    @Test
    void testFindMessages(@Qualifier("message") OwMessage mes) {
        OwMessage ent = dao.saveMessage(mes, geonameId);
        List<OwMessage> ent2List = dao.findMessages(geonameId, LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneId.systemDefault()),
            LocalDateTime.ofInstant(Instant.ofEpochSecond(Integer.MAX_VALUE), ZoneId.systemDefault()));
        assertThat(ent2List.size()).isEqualTo(1);
        assertThat(ent).isEqualTo(ent2List.get(0));
    }

    @Test
    void testFindMessageByUuid(@Qualifier("message") OwMessage ent1) {
        UUID uuid = UUID.randomUUID();
        ent1.setMessageUuid(uuid);
        ent1 = dao.saveMessage(ent1, geonameId);
        OwMessage ent2 = dao.findMessageByUuid(uuid).get();
        assertThat(ent1).isEqualTo(ent2);
    }

    @AfterEach
    void tearDown() {
        dao.deleteAllMessages();
        dao.deleteWeatherConditionCodes();
        assertThat(dao.count(OwAlert.class)).isZero();
        assertThat(dao.count(OwWeather.class)).isZero();
        assertThat(dao.count(OwDaily.class)).isZero();
        assertThat(dao.count(OwHourly.class)).isZero();
        assertThat(dao.count(OwFact.class)).isZero();
        assertThat(dao.count(OwMessage.class)).isZero();
    }

}
