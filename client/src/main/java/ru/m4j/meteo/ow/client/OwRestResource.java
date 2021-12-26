/*
 * Copyright (c) 2002-2022 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.client;

import java.util.List;

import ru.m4j.meteo.ow.model.OwCurrentDto;
import ru.m4j.meteo.ow.model.OwMessageDto;

public interface OwRestResource {

    List<OwMessageDto> getMessages(Integer geonameId, String dateFrom, String dateTo);

    OwMessageDto getMessage(String uuid);

    OwMessageDto getLastMessage(Integer geonameId);

    List<OwCurrentDto> getFacts(Integer geonameId, String dateFrom, String dateTo);
}
