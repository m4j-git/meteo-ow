/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ru.m4j.meteo.ow.domain.OwFact;
import ru.m4j.meteo.ow.domain.OwMessage;
import ru.m4j.meteo.ow.mapper.OwMessageDtoModelMapper;
import ru.m4j.meteo.ow.model.OwCurrentDto;
import ru.m4j.meteo.ow.model.OwMessageDto;
import ru.m4j.meteo.share.misc.ErrorAttribute;
import ru.m4j.meteo.share.misc.ResourceNotFoundException;

@Slf4j
@Service
public class OwMessageServiceImpl implements OwMessageService {

    private final OwDao dao;
    private final OwMessageDtoModelMapper mapper;

    public OwMessageServiceImpl(OwMessageDtoModelMapper mapper, OwDao dao) {
        this.mapper = mapper;
        this.dao = dao;
    }

    @Override
    @Transactional
    public void saveMessageToDb(final OwMessageDto dto, final Integer geonameId) {
        validGeoname(geonameId);
        final OwMessage message = dao.saveMessage(mapper.messageDtoToMessage(dto), geonameId);
        log.info("save openWeatherMap message to db - ok, id = {}", message.getMessageId());
    }

    @Override
    @Transactional
    public List<OwCurrentDto> getFacts(final Integer geonameId, String dateFrom, String dateTo) {
        validGeoname(geonameId);
        LocalDateTime ldtFrom = dateFromMapper(dateFrom);
        LocalDateTime ldtTo = dateToMapper(dateTo);
        final List<OwFact> entityList = dao.findFacts(geonameId, ldtFrom, ldtTo);
        return mapper.factsDtoFromFacts(entityList);
    }

    @Override
    @Transactional
    public OwMessageDto getLastMessage(final Integer geonameId) {
        validGeoname(geonameId);
        final OwMessage ent = dao.findLastMessage(geonameId).orElseThrow(
            () -> new ResourceNotFoundException(ErrorAttribute.MESSAGE_ERROR_CODE, ErrorAttribute.RESOURCE_NOT_FOUND_ERROR, String.valueOf(geonameId)));
        return mapper.messageDtoFromMessage(ent);
    }

    @Override
    @Transactional
    public OwMessageDto getMessage(String messageUuid) {
        validMessage(messageUuid);
        final OwMessage ent = dao.findMessageByUuid(UUID.fromString(messageUuid)).orElseThrow(
            () -> new ResourceNotFoundException(ErrorAttribute.MESSAGE_ERROR_CODE, ErrorAttribute.RESOURCE_NOT_FOUND_ERROR, messageUuid));
        return mapper.messageDtoFromMessage(ent);
    }

    @Override
    @Transactional
    public List<OwMessageDto> getMessages(final Integer geonameId, String dateFrom, String dateTo) {
        validGeoname(geonameId);
        LocalDateTime ldtFrom = dateFromMapper(dateFrom);
        LocalDateTime ldtTo = dateToMapper(dateTo);
        final List<OwMessage> entList = dao.findMessages(geonameId, ldtFrom, ldtTo);
        return mapper.messagesDtoFromMessages(entList);
    }

    private LocalDateTime dateToMapper(String dateTo) {
        return dateTo != null ? LocalDateTime.parse(dateTo)
            : LocalDateTime.ofInstant(Instant.ofEpochSecond(Integer.MAX_VALUE), ZoneId.systemDefault());
    }

    private LocalDateTime dateFromMapper(String dateFrom) {
        return dateFrom != null ? LocalDateTime.parse(dateFrom) : LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneId.systemDefault());
    }

    private void validGeoname(Integer geonameId) {
        if (geonameId == null) {
            throw new IllegalArgumentException("geonameId  is null");
        }
    }

    private void validMessage(String messageId) {
        if (messageId == null) {
            throw new IllegalArgumentException("messageId  is null");
        }
    }

}
