/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "ow_weather")
public class OwWeather implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @EqualsAndHashCode.Include
    private Short id;
    /**
     * Group of weather parameters (Rain, Snow, Extreme etc.)
     */
    @NotNull
    private String main;
    /**
     * Weather condition within the group (full list of weather conditions). Get the
     * output in your language
     */
    @NotNull
    private String description;
    /**
     * Weather icon id. How to get icons
     */
    @NotNull
    private String icon;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
