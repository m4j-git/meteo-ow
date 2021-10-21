/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "ow_weather")
public class OwWeather implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Short id;
    /**
     * Group of weather parameters (Rain, Snow, Extreme etc.)
     */
    @NotNull
    private String main;
    /**
     * Weather condition within the group (full list of weather conditions).
     * Get the output in your language
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OwWeather)) {
            return false;
        }
        OwWeather other = (OwWeather) o;
        return id != null &&
                id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
