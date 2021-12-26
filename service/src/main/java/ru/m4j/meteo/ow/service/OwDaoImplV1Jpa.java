/*
 * Copyright (c) 2002-2022 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import ru.m4j.meteo.ow.domain.OwAlert;
import ru.m4j.meteo.ow.domain.OwDaily;
import ru.m4j.meteo.ow.domain.OwFact;
import ru.m4j.meteo.ow.domain.OwHourly;
import ru.m4j.meteo.ow.domain.OwMessage;
import ru.m4j.meteo.ow.domain.OwWeather;
import ru.m4j.meteo.ow.repo.OwAlertRepository;
import ru.m4j.meteo.ow.repo.OwDailyRepository;
import ru.m4j.meteo.ow.repo.OwFactRepository;
import ru.m4j.meteo.ow.repo.OwHourlyRepository;
import ru.m4j.meteo.ow.repo.OwMessageRepository;
import ru.m4j.meteo.ow.repo.OwWeatherRepository;

@Repository
@Primary
public class OwDaoImplV1Jpa implements OwDao {

    protected final OwMessageRepository messageRepo;
    protected final OwFactRepository factRepo;
    protected final OwWeatherRepository weatherRepo;
    protected final OwAlertRepository alertRepo;
    protected final OwDailyRepository dailyRepo;
    protected final OwHourlyRepository hourlyRepo;
    protected final EntityManager em;

    private static final String QUERY_LAST_MESSAGE = "select msg from OwMessage as msg where msg.geonameId=:geoname_id "
        + "ORDER BY msg.createdOn desc";

    public OwDaoImplV1Jpa(OwMessageRepository messageRepo, OwFactRepository factRepo, OwWeatherRepository weatherRepo, OwAlertRepository alertRepo,
        OwDailyRepository dailyRepo, OwHourlyRepository hourlyRepo, EntityManager em) {
        this.messageRepo = messageRepo;
        this.factRepo = factRepo;
        this.weatherRepo = weatherRepo;
        this.alertRepo = alertRepo;
        this.dailyRepo = dailyRepo;
        this.hourlyRepo = hourlyRepo;
        this.em = em;
    }

    @Override
    @Transactional
    public OwMessage saveMessage(final OwMessage entity, final Integer geonameId) {
        entity.setGeonameId(geonameId);
        if (entity.getMessageId() == null) {
            if (entity.getMessageUuid() == null) {
                entity.setMessageUuid(UUID.randomUUID());
            }
            Long id = messageRepo.findIdByMessageUuid(entity.getMessageUuid());
            if (id != null) {
                throw new IllegalStateException("entity exists");
            }
        }
        return messageRepo.save(entity);
    }

    @Override
    @Transactional
    public void deleteAllMessages() {
        alertRepo.deleteAll();
        factRepo.deleteAll();
        dailyRepo.deleteAll();
        hourlyRepo.deleteAll();
        messageRepo.deleteAll();
    }

    @Override
    @Transactional
    public List<OwFact> findFacts(final Integer geonameId, LocalDateTime dateFrom, LocalDateTime dateTo) {
        return factRepo.findFacts(geonameId, dateFrom, dateTo);
    }

    @Override
    @Transactional
    public void saveWeatherConditionCodes(final Set<OwWeather> weather) {
        weather.forEach(weatherRepo::save);
    }

    @Override
    @Transactional
    public void deleteWeatherConditionCodes() {
        weatherRepo.deleteAll();
    }

    @Override
    @Transactional
    public Optional<OwMessage> findLastMessage(final Integer geonameId) {
        return Optional.ofNullable((OwMessage) em.createQuery(QUERY_LAST_MESSAGE)
            .setMaxResults(1)
            .setParameter("geoname_id", geonameId)
            .getSingleResult());
    }

    @Override
    public Optional<OwMessage> findMessageByUuid(UUID messageUuid) {
        return messageRepo.findMessageByUuid(messageUuid);
    }

    @Override
    public List<OwMessage> findMessages(final Integer geonameId, LocalDateTime dateFrom, LocalDateTime dateTo) {
        return messageRepo.findMessages(geonameId, dateFrom, dateTo);
    }

    @Override
    public long count(Class<?> clazz) {
        if (clazz == OwMessage.class) {
            return messageRepo.count();
        }
        if (clazz == OwFact.class) {
            return factRepo.count();
        }
        if (clazz == OwDaily.class) {
            return dailyRepo.count();
        }
        if (clazz == OwHourly.class) {
            return hourlyRepo.count();
        }
        if (clazz == OwAlert.class) {
            return alertRepo.count();
        }
        if (clazz == OwWeather.class) {
            return weatherRepo.count();
        }
        throw new IllegalStateException();
    }

    @Override
    @Transactional
    public void deleteMessages() {
        alertRepo.deleteAll();
        dailyRepo.deleteAll();
        hourlyRepo.deleteAll();
        factRepo.deleteAll();
        messageRepo.deleteAll();
    }
}
