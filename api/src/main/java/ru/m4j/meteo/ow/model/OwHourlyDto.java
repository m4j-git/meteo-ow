/*
 * Copyright (c) 2002-2022 meteo@m4j.ru
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
public class OwHourlyDto implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Time of the forecasted data, Unix, UTC
     */
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Instant dt;
    /**
     * Temperature. Units – default: kelvin, metric: Celsius, imperial: Fahrenheit.
     */
    private Double temp;
    /**
     * Temperature. This accounts for the human perception of weather. Units –
     * default: kelvin, metric: Celsius, imperial: Fahrenheit.
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
     * Atmospheric temperature (varying according to pressure and humidity) below
     * which water droplets begin to condense and dew can form. Units – default:
     * kelvin, metric: Celsius, imperial: Fahrenheit.
     */
    @JsonProperty("dew_point")
    private Double dewPoint;
    /**
     * Cloudiness, %
     */
    private Integer clouds;
    /**
     * Average visibility, metres
     */
    private Integer visibility;
    /**
     * Wind speed. Units – default: metre/sec, metric: metre/sec, imperial:
     * miles/hour
     */
    @JsonProperty("wind_speed")
    private Double windSpeed;
    /**
     * Wind gust. Units – default: metre/sec, metric: metre/sec, imperial:
     * miles/hour.
     */
    @JsonProperty("wind_gust")
    private Double windGust;
    /**
     * Wind direction, degrees (meteorological)
     */
    @JsonProperty("wind_deg")
    private Integer windDeg;
    /**
     * Probability of precipitation
     */
    private Double pop;
    /**
     * Rain volume
     */
    private OwPrecipDto rain;
    /**
     * Snow volume
     */
    private OwPrecipDto snow;
    /**
     *
     */
    private List<OwWeatherDto> weather;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
