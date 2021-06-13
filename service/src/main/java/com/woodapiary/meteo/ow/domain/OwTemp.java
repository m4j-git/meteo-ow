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

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof OwTemp) {
            return EqualsBuilder.reflectionEquals(this, obj);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

}
