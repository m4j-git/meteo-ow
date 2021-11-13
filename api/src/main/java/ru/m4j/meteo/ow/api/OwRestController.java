/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.api;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.ApiOperation;
import ru.m4j.meteo.ow.model.OwCurrentDto;
import ru.m4j.meteo.ow.model.OwMessageDto;

@RequestMapping("/api/v1")
public interface OwRestController {

    @ApiOperation(notes = "openweather messages", value = "get list of weather messages")
    @GetMapping(value = "/messages", produces = MediaType.APPLICATION_JSON_VALUE)
    List<OwMessageDto> getMessages(@RequestParam Integer geonameId, @RequestParam(required = false) String dateFrom,
            @RequestParam(required = false) String dateTo);

    @ApiOperation(notes = "openweather messages", value = "get weather message")
    @GetMapping(value = "/messages/one/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    OwMessageDto getMessage(@PathVariable(value = "uuid") String uuid);

    @ApiOperation(notes = "openweather messages", value = "get last weather message")
    @GetMapping(value = "/messages/last", produces = MediaType.APPLICATION_JSON_VALUE)
    OwMessageDto getLastMessage(@RequestParam Integer geonameId);

    @ApiOperation(notes = "openweather messages", value = "get fact weather messages")
    @GetMapping(value = "/messages/facts", produces = MediaType.APPLICATION_JSON_VALUE)
    List<OwCurrentDto> getFacts(@RequestParam Integer geonameId, @RequestParam(required = false) String dateFrom,
            @RequestParam(required = false) String dateTo);
}
