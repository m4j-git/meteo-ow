/*
 * Copyright (c) 2002-2022 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.service;

import java.util.List;

import ru.m4j.meteo.ow.model.OwCurrentDto;
import ru.m4j.meteo.ow.model.OwMessageDto;

public interface OwMessageService {

    void saveMessageToDb(OwMessageDto dto, Integer geonameId);

    List<OwCurrentDto> getFacts(Integer geonameId, String dateFrom, String dateTo);

    OwMessageDto getLastMessage(Integer geonameId);

    OwMessageDto getMessage(String messageUuid);

    List<OwMessageDto> getMessages(Integer geonameId, String dateFrom, String dateTo);
}
