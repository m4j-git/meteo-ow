/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URI;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import ru.m4j.meteo.ow.OwTestApplication;
import ru.m4j.meteo.ow.domain.OwMessage;
import ru.m4j.meteo.ow.model.OwCurrentDto;
import ru.m4j.meteo.ow.model.OwMessageDto;
import ru.m4j.meteo.ow.repo.OwFactRepository;
import ru.m4j.meteo.ow.repo.OwMessageRepository;
import ru.m4j.meteo.ow.service.OwDao;
import ru.m4j.meteo.ow.service.OwDirectoryService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = OwTestApplication.class)
class OwRestResourceTest {

    private final String messageUuid = "11111111-1111-1111-1111-111111111111";
    private final Integer geonameId = 1;
    private final String host = "localhost";
    private final String scheme = "http";
    private final String path = "/api/v1/ow";
    @LocalServerPort
    private int randomServerPort;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private OwDao dao;
    @Autowired
    private OwMessageRepository msgRepo;
    @Autowired
    private OwFactRepository factRepo;
    @Autowired
    private OwDirectoryService dir;

    @BeforeEach
    public void setUp(@Qualifier("message") OwMessage mes) throws IOException {
        assertEquals(0, factRepo.count());
        assertEquals(0, msgRepo.count());
        dir.saveConditionCodesToDb();
        dao.saveMessage(mes, geonameId);
        assertEquals(1, factRepo.count());
        assertEquals(1, msgRepo.count());
        assertThat(restTemplate).isNotNull();
        restTemplate.getRestTemplate().setErrorHandler(new OwRestTemplateResponseErrorHandler());
    }

    @Test
    public void testGetFacts() {
        URI uri = UriComponentsBuilder.newInstance().scheme(scheme).host(host).port(randomServerPort).path(path).pathSegment("messages/facts")
                .queryParam("geonameId", geonameId).buildAndExpand().toUri();
        ResponseEntity<OwCurrentDto[]> response = restTemplate.getForEntity(uri, OwCurrentDto[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertEquals(1, response.getBody().length);
    }

    @Test
    public void testGetLastMessage() {
        URI uri = UriComponentsBuilder.newInstance().scheme(scheme).host(host).port(randomServerPort).path(path).pathSegment("messages/last")
                .queryParam("geonameId", geonameId).buildAndExpand().toUri();
        ResponseEntity<OwMessageDto> response = restTemplate.getForEntity(uri, OwMessageDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void testGetMessages() {
        URI uri = UriComponentsBuilder.newInstance().scheme(scheme).host(host).port(randomServerPort).path(path).pathSegment("messages")
                .queryParam("geonameId", geonameId).buildAndExpand().toUri();
        ResponseEntity<OwMessageDto[]> response = restTemplate.getForEntity(uri, OwMessageDto[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertEquals(1, response.getBody().length);
    }

    @Test
    public void testGetMessage() {
        URI uri = UriComponentsBuilder.newInstance().scheme(scheme).host(host).port(randomServerPort).path(path).pathSegment("messages/{messageUuid}")
                .buildAndExpand(messageUuid).toUri();
        ResponseEntity<OwMessageDto> response = restTemplate.getForEntity(uri, OwMessageDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @AfterEach
    public void tearDown() {
        dao.deleteAllMessages();
        dao.deleteWeatherConditionCodes();
        assertEquals(0, factRepo.count());
        assertEquals(0, msgRepo.count());
    }

}
