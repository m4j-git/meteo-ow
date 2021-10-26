/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class OwCurrentDto implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Current time, Unix, UTC
     */
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Instant dt;
    /**
     * Sunrise time, Unix, UTC
     */
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Instant sunrise;
    /**
     * Sunset time, Unix, UTC
     */
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Instant sunset;
    /**
     * Temperature.
     * Units - default: kelvin, metric: Celsius, imperial: Fahrenheit.
     */
    private Double temp;
    /**
     * Temperature.
     * This temperature parameter accounts for the human perception of weather.
     * Units – default: kelvin, metric: Celsius, imperial: Fahrenheit.
     */
    @JsonProperty("feels_like")
    private Double feelsLike;
    /**
     * Atmospheric pressure on the sea level, hPa
     */
    private Integer pressure;
    /**
     * Humidity, %
     */
    private Integer humidity;
    /**
     * Atmospheric temperature (varying according to pressure and humidity) below which water droplets begin to condense and dew can form.
     * Units – default: kelvin, metric: Celsius, imperial: Fahrenheit.
     */
    @JsonProperty("dew_point")
    private Double dewPoint;
    /**
     * UV index
     */
    private Double uvi;
    /**
     * Cloudiness, %
     */
    private Integer clouds;
    /**
     * Average visibility, metres
     */
    private Integer visibility;
    /**
     * Wind speed. Wind speed.
     * Units – default: metre/sec, metric: metre/sec, imperial: miles/hour.
     * How to change units used
     */
    @JsonProperty("wind_speed")
    private Double windSpeed;
    /**
     * Wind direction, degrees (meteorological)
     */
    @JsonProperty("wind_deg")
    private Integer windDeg;
    /**
     * Wind gust.
     * Units – default: metre/sec, metric: metre/sec, imperial: miles/hour
     */
    @JsonProperty("wind_gust")
    private Double windGust;
    /**
     *
     */
    private List<OwWeatherDto> weather;
    /**
     * Rain volume for last hour, mm
     */
    private OwPrecipDto rain;
    /**
     * Snow volume for last hour, mm
     */
    private OwPrecipDto snow;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
