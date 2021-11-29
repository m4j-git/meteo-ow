/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.client;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import ru.m4j.meteo.ow.model.OwCurrentDto;
import ru.m4j.meteo.ow.model.OwMessageDto;

@ConditionalOnProperty(name = "meteo.client.type", havingValue = "feign", matchIfMissing = false)
@FeignClient(name = "meteo-ow")
public interface OwFeignClientImpl extends OwRestResource {

    @Override
    @GetMapping(value = "/meteo-ow/api/v1/messages", consumes = MediaType.APPLICATION_JSON_VALUE)
    List<OwMessageDto> getMessages(@RequestParam Integer geonameId, @RequestParam(required = false) String dateFrom,
        @RequestParam(required = false) String dateTo);

    @Override
    @GetMapping(value = "/meteo-ow/api/v1/messages/one/{uuid}", consumes = MediaType.APPLICATION_JSON_VALUE)
    OwMessageDto getMessage(@PathVariable("uuid") String uuid);

    @GetMapping(value = "/meteo-ow/api/v1/messages/last", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Override
    OwMessageDto getLastMessage(@RequestParam Integer geonameId);

    @GetMapping(value = "/meteo-ow/api/v1/messages/facts", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Override
    List<OwCurrentDto> getFacts(@RequestParam Integer geonameId, @RequestParam(required = false) String dateFrom,
        @RequestParam(required = false) String dateTo);

}
