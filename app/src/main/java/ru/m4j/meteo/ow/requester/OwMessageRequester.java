/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.requester;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;
import ru.m4j.meteo.ow.model.LocationDto;
import ru.m4j.meteo.ow.model.OwMessageDto;
import ru.m4j.meteo.ow.service.OwMessageService;

/**
 * https://api.openweathermap.org/data/2.5/onecall?lat={lat}&lon={lon}&exclude={part}&appid={API
 * key} lat, lon required Geographical coordinates (latitude, longitude) appid
 * required Your unique API key (you can always find it on your account page
 * under the "API key" tab) units optional Units of measurement. standard,
 * metric and imperial units are available. If you do not use the units
 * parameter, standard units will be applied by default. Learn more lang
 * optional You can use the lang parameter to get the output in your language.
 */

@Slf4j
@Service
public class OwMessageRequester {

    @Value("${meteo.provider.host:api.openweathermap.org}")
    private String host;
    @Value("${meteo.provider.scheme:https}")
    private String scheme;
    @Value("${meteo.provider.path:/data/2.5/onecall}")
    private String path;

    private final OwMessageService service;
    private final OwMessageClient client;

    @Value("${OPENWEATHERMAP_API_KEY}")
    private String apiKey;

    public OwMessageRequester(OwMessageService service, OwMessageClient client) {
        this.service = service;
        this.client = client;
    }

    // Free 60 calls/minute 1,000,000 calls/month
    public OwMessageDto requestProvider(LocationDto geo) {
        OwMessageDto dto = null;
        try {
            dto = client.request(getUri(geo));
            service.saveMessageToDb(dto, geo.getGeonameId());
            log.info("read ow weather message ok for {}", geo);
            log.debug("response: " + dto);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ow message is " + (dto == null ? "null" : dto));
        }
        return dto;
    }

    URI getUri(LocationDto geo) {
        return UriComponentsBuilder.newInstance()
            .scheme(scheme)
            .host(host)
            .path(path)
            .queryParam("lat", geo.getLat())
            .queryParam("lon", geo.getLon())
            .queryParam("appid", apiKey)
            .queryParam("exclude", "minutely")
            .queryParam("units", "metric")
            .buildAndExpand().toUri();
    }

}
