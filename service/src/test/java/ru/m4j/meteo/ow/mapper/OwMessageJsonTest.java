/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.m4j.meteo.ow.domain.OwMessage;
import ru.m4j.meteo.ow.model.OwMessageDto;
import ru.m4j.meteo.ow.srv.config.OwTestBeanFactory;
import ru.m4j.meteo.ow.srv.config.OwTestBeanSource;
import ru.m4j.meteo.ow.srv.config.OwTestDomainConfiguration;

@SpringBootTest(classes = { OwTestDomainConfiguration.class })
@Import(OwTestBeanFactory.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
class OwMessageJsonTest {

    @Autowired
    private ObjectMapper jacksonMapper;
    @Autowired
    private OwMessageDtoModelMapper mapper;
    @Autowired
    private OwTestBeanSource src;

    @BeforeEach
    void setUp() {
        assertThat(mapper).isNotNull();
    }

    @Test
    void testFullMessageJson(@Qualifier("message") OwMessage entity) throws IOException {
        OwMessageDto dto = src.readJson();
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
        OwMessageDto dto = src.readJson();
        assertThat(dto.getCurrent().getTemp()).isNotNull();
        String json = jacksonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dto);
        assertThat(json).isNotEmpty();
    }

}
