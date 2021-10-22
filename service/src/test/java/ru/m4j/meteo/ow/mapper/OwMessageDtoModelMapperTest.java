/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import ru.m4j.meteo.ow.OwTestApplication;
import ru.m4j.meteo.ow.domain.*;
import ru.m4j.meteo.ow.model.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = OwTestApplication.class)
public class OwMessageDtoModelMapperTest {

    private static final String testDataFile = "ow_onecall.json";
    @Value("${meteo.test.data.path}")
    private String testDataPath;
    @Autowired
    private OwMessageDtoModelMapper mapper;
    @Autowired
    private ObjectMapper jacksonMapper;

    @BeforeEach
    public void setUp() {
        assertNotNull(mapper);
    }

    private OwMessageDto readJson() throws IOException {
        final FileInputStream fis = new FileInputStream(testDataPath + testDataFile);
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8))) {
            return jacksonMapper.readValue(rd, OwMessageDto.class);
        }
    }

    @Test
    public void testMessageMapper() throws IOException {
        OwMessageDto dto = readJson();
        dto.setCurrent(null);
        dto.setAlerts(null);
        dto.setDaily(null);
        dto.setHourly(null);
        dto.setLat(null);
        dto.setLon(null);
        dto.setTimezone(null);
        dto.setTimezoneOffset(null);
        final OwMessage entity = mapper.messageDtoToMessage(dto);
        final OwMessageDto dto2 = mapper.messageDtoFromMessage(entity);
        dto2.setCreatedOn(null);
        dto2.setMessageUuid(null);
        assertNotNull(dto2);
        assertEquals(dto, dto2);
        assertEquals(dto.hashCode(), dto2.hashCode());
        assertTrue(dto.toString().length() > 0);
    }

    @Test
    public void testFactMapper() throws IOException {
        OwMessageDto dto = readJson();
        final OwCurrentDto dto1 = dto.getCurrent();
        final OwFact entity = mapper.factDtoToFact(dto1);
        final OwCurrentDto dto2 = mapper.factDtoFromFact(entity);
        assertNotNull(dto2);
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    public void testAlertMapper() throws IOException {
        OwMessageDto dto = readJson();
        final List<OwAlertDto> dto1 = dto.getAlerts();
        final List<OwAlert> entity = mapper.alertListDtoToAlertList(dto1);
        final List<OwAlertDto> dto2 = mapper.alertListDtoFromAlertList(entity);
        assertNotNull(dto2);
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    public void testDailyMapper() throws IOException {
        OwMessageDto dto = readJson();
        final List<OwDailyDto> dto1 = dto.getDaily();
        final List<OwDaily> entity = mapper.dailyListDtoToDailyList(dto1);
        final List<OwDailyDto> dto2 = mapper.dailyListDtoFromDailyList(entity);
        assertNotNull(dto2);
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    public void testHourlyMapper() throws IOException {
        OwMessageDto dto = readJson();
        final List<OwHourlyDto> dto1 = dto.getHourly();
        final List<OwHourly> entity = mapper.hourlyListDtoToHourlyList(dto1);
        final List<OwHourlyDto> dto2 = mapper.hourlyListDtoFromHourlyList(entity);
        assertNotNull(dto2);
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    public void testFullMessageMapper() throws IOException {
        OwMessageDto dto = readJson();
        dto.setLat(null);
        dto.setLon(null);
        dto.setTimezone(null);
        dto.setTimezoneOffset(null);
        final OwMessage entity = mapper.messageDtoToMessage(dto);
        final OwMessageDto dto2 = mapper.messageDtoFromMessage(entity);
        dto2.setCreatedOn(null);
        dto2.setMessageUuid(null);
        assertNotNull(dto2);
        assertEquals(dto, dto2);
        assertEquals(dto.hashCode(), dto2.hashCode());
        assertTrue(dto.toString().length() > 0);
    }


}
