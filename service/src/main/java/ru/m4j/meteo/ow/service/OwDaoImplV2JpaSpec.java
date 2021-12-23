/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Join;
import javax.transaction.Transactional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import ru.m4j.meteo.ow.domain.OwFact;
import ru.m4j.meteo.ow.domain.OwMessage;
import ru.m4j.meteo.ow.repo.OwAlertRepository;
import ru.m4j.meteo.ow.repo.OwDailyRepository;
import ru.m4j.meteo.ow.repo.OwFactRepository;
import ru.m4j.meteo.ow.repo.OwHourlyRepository;
import ru.m4j.meteo.ow.repo.OwMessageRepository;
import ru.m4j.meteo.ow.repo.OwWeatherRepository;

@Repository
public class OwDaoImplV2JpaSpec extends OwDaoImplV1Jpa {

    public OwDaoImplV2JpaSpec(OwMessageRepository messageRepo, OwFactRepository factRepo, OwWeatherRepository weatherRepo, OwAlertRepository alertRepo,
        OwDailyRepository dailyRepo, OwHourlyRepository hourlyRepo, EntityManager em) {
        super(messageRepo, factRepo, weatherRepo, alertRepo, dailyRepo, hourlyRepo, em);
    }

    @Override
    @Transactional
    public List<OwFact> findFacts(Integer geonameId, LocalDateTime dateFrom, LocalDateTime dateTo) {
        return factRepo.findAll(factSpecification(geonameId, dateFrom, dateTo));
    }

    @Override
    @Transactional
    public List<OwMessage> findMessages(Integer geonameId, LocalDateTime dateFrom, LocalDateTime dateTo) {
        return messageRepo.findAll(messageSpecification(geonameId, dateFrom, dateTo));
    }

    Specification<OwFact> factSpecification(Integer geonameId, LocalDateTime dateFrom, LocalDateTime dateTo) {
        return (root, query, builder) -> {
            Join<OwFact, OwMessage> join = root.join("message");
            return builder.and(builder.equal(join.get("geonameId"), geonameId), builder.between(join.get("createdOn"), dateFrom, dateTo));
        };
    }

    Specification<OwMessage> messageSpecification(Integer geonameId, LocalDateTime dateFrom, LocalDateTime dateTo) {
        return (root, query, builder) -> builder.and(builder.equal(root.get("geonameId"), geonameId),
            builder.between(root.get("createdOn"), dateFrom, dateTo));
    }

}
