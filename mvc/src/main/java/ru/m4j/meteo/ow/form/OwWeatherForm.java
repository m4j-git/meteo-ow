/*
 * Copyright (c) 2002-2022 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.form;

import java.time.Instant;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class OwWeatherForm {

    private Instant obsTime;
    private String temp;
    private String feelsLike;
    private Integer pressureMm;
    private Integer humidity;
    private Double dewPoint;
    private Double uvi;
    private Integer clouds;
    private Integer visibility;
    private Double windSpeed;
    private String windDir;
    private Double windGust;
    private String condition;
    private Double precipM1h;
    private String icon;
    private String tempWater;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
