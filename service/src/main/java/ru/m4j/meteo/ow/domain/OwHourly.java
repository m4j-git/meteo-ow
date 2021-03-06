/*
 * Copyright (c) 2002-2022 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.domain;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
@Table(name = "ow_hourly")
public class OwHourly implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "hourly_id")
    private Long hourlyId;
    /**
     * Time of the forecasted data, Unix, UTC
     */
    @NotNull
    private OffsetDateTime dt;
    /**
     * Temperature. Units – default: kelvin, metric: Celsius, imperial: Fahrenheit.
     */
    private Short temp;
    /**
     * Temperature. This accounts for the human perception of weather. Units –
     * default: kelvin, metric: Celsius, imperial: Fahrenheit.
     */
    @Column(name = "feels_like")
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
     * Atmospheric temperature (varying according to pressure and humidity) below
     * which water droplets begin to condense and dew can form. Units – default:
     * kelvin, metric: Celsius, imperial: Fahrenheit.
     */
    @Column(name = "dew_point")
    private Short dewPoint;
    /**
     * Cloudiness, %
     */
    private Short clouds;
    /**
     * Average visibility, metres
     */
    private Short visibility;
    /**
     * Wind speed. Units – default: metre/sec, metric: metre/sec, imperial:
     * miles/hour
     */
    @Column(name = "wind_speed")
    private Short windSpeed;
    /**
     * Wind gust. Units – default: metre/sec, metric: metre/sec, imperial:
     * miles/hour.
     */
    @Column(name = "wind_gust")
    private Short windGust;
    /**
     * Wind direction, degrees (meteorological)
     */
    @Column(name = "wind_deg")
    private Short windDeg;
    /**
     * Probability of precipitation
     */
    private Short pop;
    /**
     * Rain volume
     */
    private Short rain1h;
    /**
     * Snow volume
     */
    private Short snow1h;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(name = "ow_weather_hourly", joinColumns = { @JoinColumn(name = "hourly_id") }, inverseJoinColumns = { @JoinColumn(name = "weather_id") })
    private Set<OwWeather> weather = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "message_id", referencedColumnName = "message_id", nullable = false, updatable = false)
    private OwMessage message;

    public OwWeather addWeather(final OwWeather weather) {
        getWeather().add(weather);
        return weather;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
