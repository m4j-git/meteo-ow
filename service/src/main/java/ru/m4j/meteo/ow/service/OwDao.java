/*
 * Copyright (c) 2002-2022 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import ru.m4j.meteo.ow.domain.OwFact;
import ru.m4j.meteo.ow.domain.OwMessage;
import ru.m4j.meteo.ow.domain.OwWeather;

public interface OwDao {

    OwMessage saveMessage(OwMessage message, Integer geonameId);

    void deleteAllMessages();

    List<OwFact> findFacts(Integer geonameId, LocalDateTime dateFrom, LocalDateTime dateTo);

    void saveWeatherConditionCodes(Set<OwWeather> weather);

    void deleteWeatherConditionCodes();

    Optional<OwMessage> findLastMessage(Integer geonameId);

    Optional<OwMessage> findMessageByUuid(UUID uuid);

    List<OwMessage> findMessages(Integer geonameId, LocalDateTime dateFrom, LocalDateTime dateTo);

    void deleteMessages();

    long count(Class<?> clazz);
}
