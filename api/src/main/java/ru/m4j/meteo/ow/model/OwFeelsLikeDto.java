/*
 * Copyright (c) 2002-2022 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class OwFeelsLikeDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Day temperature.
     */
    @JsonProperty("day")
    private Double day;
    /**
     * Night temperature.
     */
    @JsonProperty("night")
    private Double night;
    /**
     * Evening temperature.
     */
    @JsonProperty("eve")
    private Double eve;
    /**
     * Morning temperature.
     */
    @JsonProperty("morn")
    private Double morn;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
