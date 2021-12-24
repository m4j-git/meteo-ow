/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.transaction.annotation.Transactional;

import ru.m4j.meteo.ow.domain.OwAlert;
import ru.m4j.meteo.ow.domain.OwDaily;
import ru.m4j.meteo.ow.domain.OwFact;
import ru.m4j.meteo.ow.domain.OwHourly;
import ru.m4j.meteo.ow.domain.OwMessage;
import ru.m4j.meteo.ow.domain.OwWeather;
import ru.m4j.meteo.ow.model.OwCurrentDto;
import ru.m4j.meteo.ow.model.OwMessageDto;
import ru.m4j.meteo.ow.srv.config.OwMysqlContainerBase;
import ru.m4j.meteo.ow.srv.config.OwTestBeanSource;
import ru.m4j.meteo.ow.srv.config.OwTestDaoConfiguration;

@SpringBootTest(classes = OwTestDaoConfiguration.class)
@Transactional
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
class OwMessageServiceTest extends OwMysqlContainerBase {

    private final Integer geonameId = 1;
    private final String messageUuid = "11111111-1111-1111-1111-111111111111";

    @Autowired
    private OwMessageService service;
    @Autowired
    private OwDao dao;
    @Autowired
    private OwDirectoryService dir;
    @Autowired
    private OwTestBeanSource src;

    @BeforeEach
    void setUp() throws IOException {
        assertThat(service).isNotNull();
        assertThat(dao.count(OwAlert.class)).isZero();
        assertThat(dao.count(OwWeather.class)).isZero();
        assertThat(dao.count(OwDaily.class)).isZero();
        assertThat(dao.count(OwHourly.class)).isZero();
        assertThat(dao.count(OwFact.class)).isZero();
        assertThat(dao.count(OwMessage.class)).isZero();
        OwMessageDto dto = src.readJson();
        assertThat(dto.getCurrent().getDt()).isNotNull();
        dir.saveConditionCodesToDb();
        dto.setMessageUuid(UUID.fromString(messageUuid));
        service.saveMessageToDb(dto, geonameId);
        assertThat(dao.count(OwAlert.class)).isEqualTo(2);
        assertThat(dao.count(OwWeather.class)).isEqualTo(55);
        assertThat(dao.count(OwDaily.class)).isEqualTo(1);
        assertThat(dao.count(OwHourly.class)).isEqualTo(2);
        assertThat(dao.count(OwFact.class)).isEqualTo(1);
        assertThat(dao.count(OwMessage.class)).isEqualTo(1);

    }

    @Test
    void testGetLastMessage() {
        final OwMessageDto dto = service.getLastMessage(geonameId);
        assertThat(dto).isNotNull();
        assertThat(dto.getCreatedOn()).isNotNull();
        assertThat(dto.getMessageUuid()).isNotNull();
    }

    @Test
    void testGetFacts() {
        final List<OwCurrentDto> fact2List = service.getFacts(geonameId, null, null);
        assertThat(fact2List.size()).isEqualTo(1);
        assertThat(fact2List.get(0)).isNotNull();
    }

    @Test
    void testGetMessages() {
        final List<OwMessageDto> ent2List = service.getMessages(geonameId, null, null);
        assertThat(ent2List.size()).isEqualTo(1);
        assertThat(ent2List.get(0)).isNotNull();
    }

    @Test
    void testGetMessage() {
        final OwMessageDto dto = service.getMessage(messageUuid);
        assertThat(dto).isNotNull();
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
