/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.criteria.Join;
import javax.transaction.Transactional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import ru.m4j.meteo.ow.domain.OwFact;
import ru.m4j.meteo.ow.domain.OwMessage;
import ru.m4j.meteo.ow.domain.OwWeather;
import ru.m4j.meteo.ow.repo.OwAlertRepository;
import ru.m4j.meteo.ow.repo.OwDailyRepository;
import ru.m4j.meteo.ow.repo.OwFactRepository;
import ru.m4j.meteo.ow.repo.OwHourlyRepository;
import ru.m4j.meteo.ow.repo.OwMessageRepository;
import ru.m4j.meteo.ow.repo.OwWeatherRepository;

@Repository
public class OwDaoImpl implements OwDao {

    private final OwMessageRepository messageRepo;
    private final OwFactRepository factRepo;
    private final OwWeatherRepository weatherRepo;
    private final OwAlertRepository alertRepo;
    private final OwDailyRepository dailyRepo;
    private final OwHourlyRepository hourlyRepo;

    public OwDaoImpl(OwMessageRepository messageRepo, OwFactRepository factRepo, OwWeatherRepository weatherRepo, OwAlertRepository alertRepo, OwDailyRepository dailyRepo, OwHourlyRepository hourlyRepo) {
        this.messageRepo = messageRepo;
        this.factRepo = factRepo;
        this.weatherRepo = weatherRepo;
        this.alertRepo = alertRepo;
        this.dailyRepo = dailyRepo;
        this.hourlyRepo = hourlyRepo;
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
    public List<OwFact> findFactsViaSpecification(Integer geonameId, LocalDateTime dateFrom, LocalDateTime dateTo) {
        return factRepo.findAll(factSpecification(geonameId, dateFrom, dateTo));
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
    public OwMessage findLastMessage(final Integer geonameId) {
        return messageRepo.findTopByGeonameIdOrderByCreatedOnDesc(geonameId);
    }

    @Override
    public OwMessage findMessageByUuid(UUID messageUuid) {
        return messageRepo.findMessageByUuid(messageUuid).orElseThrow();
    }

    @Override
    public List<OwMessage> findMessages(final Integer geonameId, LocalDateTime dateFrom, LocalDateTime dateTo) {
        return messageRepo.findMessages(geonameId, dateFrom, dateTo);
    }

    @Override
    @Transactional
    public List<OwMessage> findMessagesViaSpecification(Integer geonameId, LocalDateTime dateFrom, LocalDateTime dateTo) {
        return messageRepo.findAll(messageSpecification(geonameId, dateFrom, dateTo));
    }

    public Specification<OwFact> factSpecification(Integer geonameId, LocalDateTime dateFrom, LocalDateTime dateTo) {
        return (root, query, builder) -> {
            Join<OwFact, OwMessage> join = root.join("message");
            return builder.and(
                    builder.equal(join.get("geonameId"), geonameId),
                    builder.between(join.get("createdOn"), dateFrom, dateTo));
        };
    }

    public Specification<OwMessage> messageSpecification(Integer geonameId, LocalDateTime dateFrom, LocalDateTime dateTo) {
        return (root, query, builder) -> builder.and(
                builder.equal(root.get("geonameId"), geonameId),
                builder.between(root.get("createdOn"), dateFrom, dateTo));
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
