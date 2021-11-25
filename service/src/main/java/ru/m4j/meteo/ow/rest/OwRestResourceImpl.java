/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.rest;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import ru.m4j.meteo.ow.api.OwRestResource;
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
    @ApiOperation(notes = "openweather messages", value = "get list of weather messages")
    @GetMapping(value = "/messages", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OwMessageDto> getMessages(@RequestParam Integer geonameId, @RequestParam(required = false) String dateFrom,
            @RequestParam(required = false) String dateTo) {
        return messageService.getMessages(geonameId, dateFrom, dateTo);
    }

    @ApiOperation(notes = "openweather messages", value = "get weather message")
    @GetMapping(value = "/messages/one/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public OwMessageDto getMessage(@PathVariable("uuid") String uuid) {
        return messageService.getMessage(uuid);
    }

    @ApiOperation(notes = "openweather messages", value = "get last weather message")
    @GetMapping(value = "/messages/last", produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public OwMessageDto getLastMessage(@RequestParam Integer geonameId) {
        return messageService.getLastMessage(geonameId);
    }

    @ApiOperation(notes = "openweather messages", value = "get fact weather messages")
    @GetMapping(value = "/messages/facts", produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public List<OwCurrentDto> getFacts(@RequestParam Integer geonameId, @RequestParam(required = false) String dateFrom,
            @RequestParam(required = false) String dateTo) {
        return messageService.getFacts(geonameId, dateFrom, dateTo);
    }

}
