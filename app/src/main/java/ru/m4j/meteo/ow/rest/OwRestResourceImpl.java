/*
 * Copyright (c) 2002-2022 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.rest;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import ru.m4j.meteo.ow.client.OwRestResource;
import ru.m4j.meteo.ow.model.OwCurrentDto;
import ru.m4j.meteo.ow.model.OwMessageDto;
import ru.m4j.meteo.ow.service.OwMessageService;

@RestController
@RequestMapping("/api/v1")
public class OwRestResourceImpl implements OwRestResource {

    private final OwMessageService messageService;

    public OwRestResourceImpl(OwMessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    @Operation(tags = "openweather messages", summary = "get list of weather messages")
    @GetMapping(value = "/messages", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OwMessageDto> getMessages(@RequestParam Integer geonameId, @RequestParam(required = false) String dateFrom,
        @RequestParam(required = false) String dateTo) {
        return messageService.getMessages(geonameId, dateFrom, dateTo);
    }

    @Operation(tags = "openweather messages", summary = "get weather message")
    @GetMapping(value = "/messages/one/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public OwMessageDto getMessage(@PathVariable("uuid") String uuid) {
        return messageService.getMessage(uuid);
    }

    @Operation(tags = "openweather messages", summary = "get last weather message")
    @GetMapping(value = "/messages/last", produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public OwMessageDto getLastMessage(@RequestParam Integer geonameId) {
        return messageService.getLastMessage(geonameId);
    }

    @Operation(tags = "openweather messages", summary = "get fact weather messages")
    @GetMapping(value = "/messages/facts", produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public List<OwCurrentDto> getFacts(@RequestParam Integer geonameId, @RequestParam(required = false) String dateFrom,
        @RequestParam(required = false) String dateTo) {
        return messageService.getFacts(geonameId, dateFrom, dateTo);
    }

}
