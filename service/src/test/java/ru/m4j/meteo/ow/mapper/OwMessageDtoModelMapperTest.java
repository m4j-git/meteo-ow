/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import ru.m4j.meteo.ow.OwTestApplication;
import ru.m4j.meteo.ow.domain.OwAlert;
import ru.m4j.meteo.ow.domain.OwDaily;
import ru.m4j.meteo.ow.domain.OwFact;
import ru.m4j.meteo.ow.domain.OwHourly;
import ru.m4j.meteo.ow.domain.OwMessage;
import ru.m4j.meteo.ow.model.OwAlertDto;
import ru.m4j.meteo.ow.model.OwCurrentDto;
import ru.m4j.meteo.ow.model.OwDailyDto;
import ru.m4j.meteo.ow.model.OwHourlyDto;
import ru.m4j.meteo.ow.model.OwMessageDto;
import ru.m4j.meteo.share.app.GlobalConstants;

@Slf4j
@SpringBootTest(classes = OwTestApplication.class)
class OwMessageDtoModelMapperTest {

    private static final String TEST_DATA_FILE = "ow_onecall.json";
    @Autowired
    private OwMessageDtoModelMapper mapper;
    @Autowired
    private ObjectMapper jacksonMapper;

    @BeforeEach
    public void setUp() {
        assertThat(mapper).isNotNull();
    }

    private OwMessageDto readJson() throws IOException {
        final FileInputStream fis = new FileInputStream(GlobalConstants.TEST_DATA_PATH + TEST_DATA_FILE);
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8))) {
            return jacksonMapper.readValue(rd, OwMessageDto.class);
        }
    }

    @Test
    void testMessageMapper() throws IOException {
        OwMessageDto dto = readJson();
        dto.setCurrent(null);
        dto.setAlerts(Collections.emptyList());
        dto.setDaily(Collections.emptyList());
        dto.setHourly(Collections.emptyList());
        dto.setLat(null);
        dto.setLon(null);
        dto.setTimezone(null);
        dto.setTimezoneOffset(null);
        final OwMessage entity = mapper.messageDtoToMessage(dto);
        final OwMessageDto dto2 = mapper.messageDtoFromMessage(entity);
        dto2.setCreatedOn(null);
        dto2.setMessageUuid(null);
        assertThat(dto2).isNotNull()
            .isEqualTo(dto)
            .hasSameHashCodeAs(dto);
        log.debug("result: " + dto2);
    }

    @Test
    void testFactMapper() throws IOException {
        OwMessageDto dto = readJson();
        final OwCurrentDto dto1 = dto.getCurrent();
        final OwFact entity = mapper.factDtoToFact(dto1);
        final OwCurrentDto dto2 = mapper.factDtoFromFact(entity);
        assertThat(dto1).isNotNull()
            .isEqualTo(dto2)
            .hasSameHashCodeAs(dto2);
    }

    @Test
    void testAlertMapper() throws IOException {
        OwMessageDto dto = readJson();
        final List<OwAlertDto> dto1 = dto.getAlerts();
        final List<OwAlert> entity = mapper.alertListDtoToAlertList(dto1);
        final List<OwAlertDto> dto2 = mapper.alertListDtoFromAlertList(entity);
        assertThat(dto2).isNotNull()
            .isEqualTo(dto1)
            .hasSameHashCodeAs(dto1);
    }

    @Test
    void testDailyMapper() throws IOException {
        OwMessageDto dto = readJson();
        final List<OwDailyDto> dto1 = dto.getDaily();
        final List<OwDaily> entity = mapper.dailyListDtoToDailyList(dto1);
        final List<OwDailyDto> dto2 = mapper.dailyListDtoFromDailyList(entity);
        assertThat(dto2).isNotNull()
            .isEqualTo(dto1)
            .hasSameHashCodeAs(dto1);
    }

    @Test
    void testHourlyMapper() throws IOException {
        OwMessageDto dto = readJson();
        final List<OwHourlyDto> dto1 = dto.getHourly();
        final List<OwHourly> entity = mapper.hourlyListDtoToHourlyList(dto1);
        final List<OwHourlyDto> dto2 = mapper.hourlyListDtoFromHourlyList(entity);
        assertThat(dto2).isNotNull()
            .isEqualTo(dto1)
            .hasSameHashCodeAs(dto1);
    }

    @Test
    void testFullMessageMapper() throws IOException {
        OwMessageDto dto = readJson();
        dto.setLat(null);
        dto.setLon(null);
        dto.setTimezone(null);
        dto.setTimezoneOffset(null);
        final OwMessage entity = mapper.messageDtoToMessage(dto);
        final OwMessageDto dto2 = mapper.messageDtoFromMessage(entity);
        dto2.setCreatedOn(null);
        dto2.setMessageUuid(null);
        assertThat(dto2).isNotNull()
            .isEqualTo(dto)
            .hasSameHashCodeAs(dto);
        log.info("dto " + dto2);
    }

}
