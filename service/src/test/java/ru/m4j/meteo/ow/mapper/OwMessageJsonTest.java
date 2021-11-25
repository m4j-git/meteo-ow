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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.m4j.meteo.ow.OwTestApplication;
import ru.m4j.meteo.ow.domain.OwMessage;
import ru.m4j.meteo.ow.model.OwMessageDto;
import ru.m4j.meteo.share.app.GlobalConstants;

@SpringBootTest(classes = OwTestApplication.class)
class OwMessageJsonTest {

    private static final String TEST_DATA_FILE = "ow_onecall.json";
    @Autowired
    private ObjectMapper jacksonMapper;
    @Autowired
    private OwMessageDtoModelMapper mapper;

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
    void testFullMessageJson(@Qualifier("message") OwMessage entity) throws IOException {
        OwMessageDto dto = readJson();
        dto.setLat(null);
        dto.setLon(null);
        dto.setTimezone(null);
        dto.setTimezoneOffset(null);
        entity.setMessageUuid(null);
        entity.setCreatedOn(null);
        final OwMessageDto dto2 = mapper.messageDtoFromMessage(entity);
        assertThat(dto2).isNotNull();
        assertThat(dto).isEqualTo(dto2);
    }

    @Test
    void testJacksonMapper() throws IOException {
        OwMessageDto dto = readJson();
        assertThat(dto.getCurrent().getTemp()).isNotNull();
        String json = jacksonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dto);
        assertThat(json.length() > 10).isTrue();
    }

}
