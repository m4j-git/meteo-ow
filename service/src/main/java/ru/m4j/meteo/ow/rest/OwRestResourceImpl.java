/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.rest;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import ru.m4j.meteo.ow.api.OwRestController;
import ru.m4j.meteo.ow.model.OwCurrentDto;
import ru.m4j.meteo.ow.model.OwMessageDto;
import ru.m4j.meteo.ow.service.OwMessageService;

@RestController
public class OwRestResourceImpl implements OwRestController {

    private final OwMessageService messageService;

    public OwRestResourceImpl(OwMessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public List<OwMessageDto> getMessages(Integer geonameId, String dateFrom, String dateTo) {
        return messageService.getMessages(geonameId, dateFrom, dateTo);
    }

    @Override
    public OwMessageDto getMessage(String id) {
        return messageService.getMessage(id);
    }

    @Override
    public OwMessageDto getLastMessage(Integer geonameId) {
        return messageService.getLastMessage(geonameId);
    }

    @Override
    public List<OwCurrentDto> getFacts(Integer geonameId, String dateFrom, String dateTo) {
        return messageService.getFacts(geonameId, dateFrom, dateTo);
    }

}
