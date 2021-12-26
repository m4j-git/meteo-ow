/*
 * Copyright (c) 2002-2022 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.m4j.meteo.ow.domain.OwFact;

@Repository
public interface OwFactRepository extends JpaRepository<OwFact, Long>, JpaSpecificationExecutor<OwFact> {

    @Query("select fact from OwFact as fact where fact.message.messageId in "
        + "(select messageId from OwMessage where geonameId=:geoname_id and createdOn BETWEEN :date_from AND :date_to)")
    List<OwFact> findFacts(@Param("geoname_id") Integer geonameId, @Param("date_from") LocalDateTime dateFrom,
        @Param("date_to") LocalDateTime dateTo);
}
