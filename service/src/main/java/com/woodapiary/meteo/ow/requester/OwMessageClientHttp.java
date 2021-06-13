/*
 * Copyright (c) 2002-2021 meteo@woodapiary.com
 */
package com.woodapiary.meteo.ow.requester;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woodapiary.meteo.ow.model.OwMessageDto;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

@ConditionalOnProperty(name = "meteo.client", havingValue = "http")
@Component
public class OwMessageClientHttp implements OwMessageClient {

    private final ObjectMapper jacksonMapper;

    public OwMessageClientHttp(ObjectMapper jacksonMapper) {
        this.jacksonMapper = jacksonMapper;
    }


    @Override
    public OwMessageDto request(URI uri) {
        try {
            URLConnection connection = uri.toURL().openConnection();
            try (InputStream is = connection.getInputStream();
                 BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                final OwMessageDto owDto = jacksonMapper.readValue(rd, OwMessageDto.class);
                rd.close();
                return owDto;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
