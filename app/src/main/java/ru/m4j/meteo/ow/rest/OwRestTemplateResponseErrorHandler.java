/*
 * Copyright (c) 2002-2022 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.rest;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

@Component
public class OwRestTemplateResponseErrorHandler implements ResponseErrorHandler {

    private static final Logger log = LoggerFactory.getLogger(OwRestTemplateResponseErrorHandler.class);

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
        return ((httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR)
            || (httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR));
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
        log.error(String.format("%s, %s", httpResponse.getStatusCode(), httpResponse.getStatusText()));
    }
}
