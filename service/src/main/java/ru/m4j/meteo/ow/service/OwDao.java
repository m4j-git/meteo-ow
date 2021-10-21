/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.service;

import ru.m4j.meteo.ow.domain.OwFact;
import ru.m4j.meteo.ow.domain.OwMessage;
import ru.m4j.meteo.ow.domain.OwWeather;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface OwDao {
    OwMessage saveMessage(OwMessage message, Integer geonameId);

    void deleteAllMessages();

    List<OwFact> findFacts(Integer geonameId, LocalDateTime dateFrom, LocalDateTime dateTo);

    List<OwFact> findFactsViaSpecification(Integer geonameId, LocalDateTime dateFrom, LocalDateTime dateTo);

    void saveWeatherConditionCodes(Set<OwWeather> weather);

    void deleteWeatherConditionCodes();

    OwMessage findLastMessage(Integer geonameId);

    OwMessage findMessageByUuid(UUID uuid);

    List<OwMessage> findMessages(Integer geonameId, LocalDateTime dateFrom, LocalDateTime dateTo);

    List<OwMessage> findMessagesViaSpecification(Integer geonameId, LocalDateTime dateFrom, LocalDateTime dateTo);

    public void deleteMessages();
}
