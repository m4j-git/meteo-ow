/*
 * Copyright (c) 2002-2022 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.requester;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.transaction.annotation.Transactional;

import ru.m4j.meteo.ow.model.LocationDto;
import ru.m4j.meteo.ow.model.OwMessageDto;
import ru.m4j.meteo.ow.repo.OwMessageRepository;
import ru.m4j.meteo.ow.service.OwDao;
import ru.m4j.meteo.ow.service.OwDirectoryService;
import ru.m4j.meteo.ow.srv.config.OwMysqlContainerBase;
import ru.m4j.meteo.ow.srv.config.OwTestBeanSource;

@SpringBootTest
@Transactional
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
class OwMessageRequesterIT extends OwMysqlContainerBase {

    @MockBean
    private OwMessageClient client;
    @Autowired
    private OwMessageRequester requester;
    @Autowired
    private OwTestBeanSource src;
    @Autowired
    private OwDao dao;
    @Autowired
    private OwMessageRepository msgRepo;
    @Autowired
    private OwDirectoryService dir;

    @BeforeEach
    public void setUp() throws IOException {
        assertThat(requester).isNotNull();
        assertThat(msgRepo.count()).isZero();
        dir.saveConditionCodesToDb();
    }

    @Test
    void testRequestProvider(@Autowired LocationDto geoname) throws IOException {
        when(client.request(requester.getUri(geoname))).thenReturn(src.readJson());
        OwMessageDto result = requester.requestProvider(geoname);
        assertThat(result.getCurrent().getDt()).isNotNull();
    }

    @AfterEach
    public void tearDown() {
        dao.deleteMessages();
        dao.deleteWeatherConditionCodes();
        assertThat(msgRepo.count()).isZero();
    }

}
