/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.requester;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.m4j.meteo.ow.model.OwMessageDto;

import java.net.URI;

@ConditionalOnProperty(name = "meteo.client", havingValue = "rest", matchIfMissing = true)
@Component
public class OwMessageClientRestTemplate implements OwMessageClient {

    private final RestTemplate restTemplate;

    public OwMessageClientRestTemplate(RestTemplateBuilder rtBuilder) {
        this.restTemplate = rtBuilder
                .additionalInterceptors((request, body, execution) -> execution.execute(request, body))
                .build();
    }

    @Override
    public OwMessageDto request(URI uri) {
        return restTemplate.getForObject(uri, OwMessageDto.class);
    }

}
