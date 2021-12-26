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
public class OwFeelsLike implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Day temperature.
     */
    @Column(name = "feels_like_day")
    private Short day;
    /**
     * Night temperature.
     */
    @Column(name = "feels_like_night")
    private Short night;
    /**
     * Evening temperature.
     */
    @Column(name = "feels_like_eve")
    private Short eve;
    /**
     * Morning temperature.
     */
    @Column(name = "feels_like_morn")
    private Short morn;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
