/*
 * Copyright (c) 2002-2022 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class OwTemp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Day temperature.
     */
    @Column(name = "temp_day")
    private Short day;
    /**
     * Min daily temperature.
     */
    @Column(name = "temp_min")
    private Short min;
    /**
     * Max daily temperature.
     */
    @Column(name = "temp_max")
    private Short max;
    /**
     * Night temperature.
     */
    @Column(name = "temp_night")
    private Short night;
    /**
     * Evening temperature.
     */
    @Column(name = "temp_eve")
    private Short eve;
    /**
     * Morning temperature.
     */
    @Column(name = "temp_morn")
    private Short morn;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
