/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class OwTempDto implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Day temperature.
     */
    private Double day;
    /**
     * Min daily temperature.
     */
    private Double min;
    /**
     * Max daily temperature.
     */
    private Double max;
    /**
     * Night temperature.
     */
    private Double night;
    /**
     * Evening temperature.
     */
    private Double eve;
    /**
     * Morning temperature.
     */
    private Double morn;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
