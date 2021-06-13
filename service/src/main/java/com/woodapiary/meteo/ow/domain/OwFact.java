/*
 * Copyright (c) 2002-2021 meteo@woodapiary.com
 */
package com.woodapiary.meteo.ow.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "ow_fact")
@EntityListeners(AuditingEntityListener.class)
public class OwFact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long factId;
    /**
     * Current time, Unix, UTC
     */
    @NotNull
    private OffsetDateTime dt;
    /**
     * Sunrise time, Unix, UTC
     */
    private OffsetDateTime sunrise;
    /**
     * Sunset time, Unix, UTC
     */
    private OffsetDateTime sunset;
    /**
     * Temperature.
     * Units - default: kelvin, metric: Celsius, imperial: Fahrenheit.
     */
    private Short temp;
    /**
     * Temperature.
     * This temperature parameter accounts for the human perception of weather.
     * Units – default: kelvin, metric: Celsius, imperial: Fahrenheit.
     */
    private Short feelsLike;
    /**
     * Atmospheric pressure on the sea level, hPa
     */
    private Short pressure;
    /**
     * Humidity, %
     */
    private Short humidity;
    /**
     * Atmospheric temperature (varying according to pressure and humidity) below which water droplets begin to condense and dew can form.
     * Units – default: kelvin, metric: Celsius, imperial: Fahrenheit.
     */
    private Short dewPoint;
    /**
     * UV index
     */
    private Short uvi;
    /**
     * Cloudiness, %
     */
    private Short clouds;
    /**
     * Average visibility, metres
     */
    private Short visibility;
    /**
     * Wind speed. Wind speed.
     * Units – default: metre/sec, metric: metre/sec, imperial: miles/hour.
     * How to change units used
     */
    private Short windSpeed;
    /**
     * Wind direction, degrees (meteorological)
     */
    private Short windDeg;
    /**
     * Wind gust.
     * Units – default: metre/sec, metric: metre/sec, imperial: miles/hour
     */
    private Short windGust;
    /**
     * Rain volume for last hour, mm
     */
    private Short rain1h;
    /**
     * Snow volume for last hour, mm
     */
    private Short snow1h;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
    @JoinTable(name = "ow_weather_fact",
            joinColumns = @JoinColumn(name = "fact_id", nullable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(name = "weather_id", nullable = false, updatable = false))

    private Set<OwWeather> weather = new HashSet<>();

    @NotNull
    @OneToOne
    @JoinColumn(name = "message_id", referencedColumnName = "message_id", nullable = false, unique = true, updatable = false)
    private OwMessage message;

    public OwWeather addWeather(final OwWeather weather) {
        getWeather().add(weather);
        return weather;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OwFact)) {
            return false;
        }
        OwFact other = (OwFact) o;
        return factId != null &&
                factId.equals(other.getFactId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(factId);
    }

}
