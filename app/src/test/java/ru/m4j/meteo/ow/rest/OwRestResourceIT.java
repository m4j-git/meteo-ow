/*
 * Copyright (c) 2002-2022 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.rest;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.web.util.UriComponentsBuilder;

import ru.m4j.meteo.ow.domain.OwFact;
import ru.m4j.meteo.ow.domain.OwMessage;
import ru.m4j.meteo.ow.model.OwCurrentDto;
import ru.m4j.meteo.ow.model.OwMessageDto;
import ru.m4j.meteo.ow.service.OwDao;
import ru.m4j.meteo.ow.service.OwDirectoryService;
import ru.m4j.meteo.ow.srv.config.OwMysqlContainerBase;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
class OwRestResourceIT extends OwMysqlContainerBase {

    private final String messageUuid = "11111111-1111-1111-1111-111111111111";
    private final Integer geonameId = 1;
    private final String host = "localhost";
    private final String scheme = "http";
    private final String path = "/meteo-ow/api/v1";
    @LocalServerPort
    private int randomServerPort;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private OwDao dao;
    @Autowired
    private OwDirectoryService dir;

    @BeforeEach
    public void setUp(@Qualifier("message") OwMessage mes) throws IOException {
        assertThat(dao.count(OwFact.class)).isZero();
        assertThat(dao.count(OwMessage.class)).isZero();
        dir.saveConditionCodesToDb();
        dao.saveMessage(mes, geonameId);
        assertThat(dao.count(OwFact.class)).isEqualTo(1);
        assertThat(dao.count(OwMessage.class)).isEqualTo(1);
        assertThat(restTemplate).isNotNull();
        restTemplate.getRestTemplate().setErrorHandler(new OwRestTemplateResponseErrorHandler());
    }

    @Test
    void testGetFacts() {
        URI uri = UriComponentsBuilder.newInstance().scheme(scheme).host(host).port(randomServerPort).path(path).pathSegment("messages/facts")
            .queryParam("geonameId", geonameId).buildAndExpand().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> request = new HttpEntity<>(headers, null);
        ResponseEntity<OwCurrentDto[]> response = restTemplate.exchange(uri, HttpMethod.GET, request, OwCurrentDto[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(1);
    }

    @Test
    void testGetLastMessage() {
        URI uri = UriComponentsBuilder.newInstance().scheme(scheme).host(host).port(randomServerPort).path(path).pathSegment("messages/last")
            .queryParam("geonameId", geonameId).buildAndExpand().toUri();
        ResponseEntity<OwMessageDto> response = restTemplate.getForEntity(uri, OwMessageDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void testGetMessages() {
        URI uri = UriComponentsBuilder.newInstance().scheme(scheme).host(host).port(randomServerPort).path(path).pathSegment("messages")
            .queryParam("geonameId", geonameId).buildAndExpand().toUri();
        ResponseEntity<OwMessageDto[]> response = restTemplate.getForEntity(uri, OwMessageDto[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(1);
    }

    @Test
    void testGetMessage() {
        URI uri = UriComponentsBuilder.newInstance().scheme(scheme).host(host).port(randomServerPort).path(path).pathSegment("messages/one/{messageUuid}")
            .buildAndExpand(messageUuid).toUri();
        ResponseEntity<OwMessageDto> response = restTemplate.getForEntity(uri, OwMessageDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @AfterEach
    public void tearDown() {
        dao.deleteAllMessages();
        dao.deleteWeatherConditionCodes();
        assertThat(dao.count(OwFact.class)).isZero();
        assertThat(dao.count(OwMessage.class)).isZero();
    }

}
