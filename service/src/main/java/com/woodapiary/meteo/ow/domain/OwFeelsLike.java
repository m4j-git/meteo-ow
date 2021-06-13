/*
 * Copyright (c) 2002-2021 meteo@woodapiary.com
 */
package com.woodapiary.meteo.ow.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
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

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof OwFeelsLike) {
            return EqualsBuilder.reflectionEquals(this, obj);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

}

