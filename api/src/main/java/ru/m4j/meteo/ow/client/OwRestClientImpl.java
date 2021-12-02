/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.client;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;
import ru.m4j.meteo.ow.model.OwCurrentDto;
import ru.m4j.meteo.ow.model.OwMessageDto;

@ConditionalOnProperty(name = "meteo.client.type", havingValue = "resttemplate", matchIfMissing = false)
@Service
@Slf4j
public class OwRestClientImpl implements OwRestResource {

    private final RestTemplate restTemplate;

    @Value("${meteo.ow-rest-client.host:meteo-host}")
    private String host;
    @Value("${meteo.ow-rest-client.scheme:http}")
    private String scheme;
    @Value("${meteo.ow-rest-client.path:/meteo-ow/api/v1}")
    private String path;
    @Value("${meteo.ow-rest-client.port:8081}")
    private Integer port;

    public OwRestClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    URI getUri(String segment, Object... param) {
        return UriComponentsBuilder.newInstance().scheme(scheme).host(host).port(port).path(path).pathSegment(segment)
            .queryParam("geonameId", param[0]).buildAndExpand().toUri();
    }

    @Override
    public List<OwCurrentDto> getFacts(Integer geonameId, String dateFrom, String dateTo) {
        URI uri = getUri("messages/facts", geonameId);
        ResponseEntity<OwCurrentDto[]> response = restTemplate.getForEntity(uri, OwCurrentDto[].class);
        if ((response.getStatusCode() == HttpStatus.OK) && response.hasBody()) {
            return Arrays.asList(response.getBody());
        }
        return Collections.emptyList();
    }

    @Override
    public OwMessageDto getLastMessage(Integer geonameId) {
        URI uri = getUri("messages/last", geonameId);
        ResponseEntity<OwMessageDto> response = restTemplate.getForEntity(uri, OwMessageDto.class);
        if ((response.getStatusCode() == HttpStatus.OK) && response.hasBody()) {
            log.debug("response: " + response.getBody());
            return response.getBody();
        }
        return null;
    }

    @Override
    public List<OwMessageDto> getMessages(Integer geonameId, String dateFrom, String dateTo) {
        URI uri = getUri("messages", geonameId);
        ResponseEntity<OwMessageDto[]> response = restTemplate.getForEntity(uri, OwMessageDto[].class);
        if ((response.getStatusCode() == HttpStatus.OK) && response.hasBody()) {
            return Arrays.asList(response.getBody());
        }
        return Collections.emptyList();
    }

    @Override
    public OwMessageDto getMessage(String uuid) {
        throw new IllegalStateException("not implemented");
    }

}
