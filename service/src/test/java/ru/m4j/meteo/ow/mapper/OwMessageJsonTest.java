/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import ru.m4j.meteo.ow.OwTestApplication;
import ru.m4j.meteo.ow.domain.OwMessage;
import ru.m4j.meteo.ow.model.OwMessageDto;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = OwTestApplication.class)
class OwMessageJsonTest {

    private static final String testDataFile = "ow_onecall.json";
    @Autowired
    private ObjectMapper jacksonMapper;
    @Value("${meteo.test.data.path}")
    private String testDataPath;
    @Autowired
    private OwMessageDtoModelMapper mapper;

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
    public void testFullMessageJson(@Qualifier("message") OwMessage entity) throws IOException {
        OwMessageDto dto = readJson();
        dto.setLat(null);
        dto.setLon(null);
        dto.setTimezone(null);
        dto.setTimezoneOffset(null);
        entity.setMessageUuid(null);
        entity.setCreatedOn(null);
        final OwMessageDto dto2 = mapper.messageDtoFromMessage(entity);
        assertNotNull(dto2);
        assertEquals(dto, dto2);
    }

    @Test
    public void testJacksonMapper() throws IOException {
        OwMessageDto dto = readJson();
        assertThat(dto.getCurrent().getTemp()).isNotNull();
        String json = jacksonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dto);
        assertTrue(json.length() > 10);
    }


}
