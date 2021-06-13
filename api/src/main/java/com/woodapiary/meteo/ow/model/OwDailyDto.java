/*
 * Copyright (c) 2002-2021 meteo@woodapiary.com
 */
package com.woodapiary.meteo.ow.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class OwDailyDto implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Time of the forecasted data, Unix, UTC
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
     * Units – default: kelvin, metric: Celsius, imperial: Fahrenheit.
     */
    private OwTempDto temp;
    /**
     * This accounts for the human perception of weather. Units – default: kelvin, metric
     */
    @JsonProperty("feels_like")
    private OwFeelsLikeDto feelsLike;
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
     * Wind speed.
     * Units – default: metre/sec, metric: metre/sec, imperial: miles/hour.
     */
    @JsonProperty("wind_speed")
    private Double windSpeed;
    /**
     * Wind gust. Units – default: metre/sec, metric: metre/sec, imperial: miles/hour
     */
    @JsonProperty("wind_gust")
    private Double windGust;
    /**
     * Wind direction, degrees (meteorological)
     */
    @JsonProperty("wind_deg")
    private Integer windDeg;
    /**
     *
     */
    private List<OwWeatherDto> weather;
    /**
     * Cloudiness, %
     */
    private Integer clouds;
    /**
     * Probability of precipitation
     */
    private Double pop;
    /**
     * Midday UV index
     */
    private Double uvi;
    /**
     * Average visibility, metres
     */
    private Integer visibility;
    /**
     * Precipitation volume, mm
     */
    private Double rain;
    /**
     * Precipitation volume, mm
     */
    private Double snow;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
