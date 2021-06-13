/*
 * Copyright (c) 2002-2021 meteo@woodapiary.com
 */
package com.woodapiary.meteo.ow.rest;

import com.woodapiary.meteo.ow.api.OwRestController;
import com.woodapiary.meteo.ow.model.OwCurrentDto;
import com.woodapiary.meteo.ow.model.OwMessageDto;
import com.woodapiary.meteo.ow.service.OwMessageService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
