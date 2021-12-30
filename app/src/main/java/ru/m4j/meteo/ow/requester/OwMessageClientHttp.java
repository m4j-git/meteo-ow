/*
 * Copyright (c) 2002-2022 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.requester;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

import javax.annotation.PostConstruct;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import ru.m4j.meteo.ow.model.OwMessageDto;

@Slf4j
@ConditionalOnProperty(name = "meteo.provider.type", havingValue = "http")
@Component
public class OwMessageClientHttp implements OwMessageClient {

    private final ObjectMapper jacksonMapper;

    public OwMessageClientHttp(ObjectMapper jacksonMapper) {
        this.jacksonMapper = jacksonMapper;
    }

    @Override
    public OwMessageDto request(URI uri) throws IOException {
        URLConnection connection = uri.toURL().openConnection();
        try (InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            return jacksonMapper.readValue(rd, OwMessageDto.class);
        }
    }

    @PostConstruct
    void init() {
        log.info(this.getClass().getCanonicalName() + " inited");
    }

}
