/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.repo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.m4j.meteo.ow.domain.OwMessage;

@Repository
public interface OwMessageRepository extends JpaRepository<OwMessage, Long>, JpaSpecificationExecutor<OwMessage> {

    @Query("select msg from OwMessage as msg  where msg.geonameId=:geoname_id "
        + "and msg.createdOn BETWEEN :date_from AND :date_to ORDER BY msg.createdOn desc")
    List<OwMessage> findMessages(@Param("geoname_id") Integer geonameId, @Param("date_from") LocalDateTime dateFrom,
        @Param("date_to") LocalDateTime dateTo);

    @Query("select msg.messageId from OwMessage as msg  where msg.messageUuid= :message_uuid")
    Long findIdByMessageUuid(@Param("message_uuid") UUID uuid);

    @Query("select msg from OwMessage as msg  where msg.messageUuid= :message_uuid")
    Optional<OwMessage> findMessageByUuid(@Param("message_uuid") UUID uuid);
}
