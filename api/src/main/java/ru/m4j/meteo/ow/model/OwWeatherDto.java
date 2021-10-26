/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class OwWeatherDto implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Weather condition id
     */
    private Integer id;
    /**
     * Group of weather parameters (Rain, Snow, Extreme etc.)
     */
    private String main;
    /**
     * Weather condition within the group (full list of weather conditions).
     * Get the output in your language
     */
    private String description;
    /**
     * Weather icon id. How to get icons
     */
    private String icon;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
