/*
 * Copyright (c) 2002-2022 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.requester;

import java.net.URI;

import javax.annotation.PostConstruct;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import ru.m4j.meteo.ow.model.OwMessageDto;

@Slf4j
@ConditionalOnProperty(name = "meteo.provider.type", havingValue = "resttemplate", matchIfMissing = true)
@Component
public class OwMessageClientRestTemplate implements OwMessageClient {

    private final RestTemplate restTemplate;

    public OwMessageClientRestTemplate(RestTemplateBuilder rtBuilder) {
        this.restTemplate = rtBuilder.additionalInterceptors((request, body, execution) -> execution.execute(request, body)).build();
    }

    @Override
    public OwMessageDto request(URI uri) {
        return restTemplate.getForObject(uri, OwMessageDto.class);
    }

    @PostConstruct
    void init() {
        log.info(this.getClass().getCanonicalName() + " inited");
    }
}
