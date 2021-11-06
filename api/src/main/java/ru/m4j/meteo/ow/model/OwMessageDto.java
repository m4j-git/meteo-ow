/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class OwMessageDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * message_uuid Уникальный идентификатор Строка
     */
    @JsonProperty("message_uuid")
    private UUID messageUuid;
    /**
     * created_on Временная метка запроса провайдера Строка
     */
    @JsonProperty("created_on")
    private Instant createdOn;
    /**
     * Geographical coordinates of the location (latitude)
     */
    private Double lat;
    /**
     * Geographical coordinates of the location (longitude)
     */
    private Double lon;
    /**
     * Timezone name for the requested location
     */
    private String timezone;
    /**
     * timezone_offset Shift in seconds from UTC
     */
    @JsonProperty("timezone_offset")
    private Integer timezoneOffset;
    /**
     * Current weather data API response
     */
    private OwCurrentDto current;
    /**
     * Hourly forecast weather data API response
     */
    private List<OwHourlyDto> hourly;
    /**
     * Daily forecast weather data API response
     */
    private List<OwDailyDto> daily;
    /**
     * Government weather alerts data from major national weather warning systems
     */
    private List<OwAlertDto> alerts;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
