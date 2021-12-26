/*
 * Copyright (c) 2002-2022 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.form;

import java.util.List;

import org.springframework.stereotype.Component;

import ru.m4j.meteo.ow.model.OwCurrentDto;
import ru.m4j.meteo.ow.model.OwDailyDto;
import ru.m4j.meteo.ow.model.OwMessageDto;
import ru.m4j.meteo.ow.model.OwWeatherDto;

@Component
public class OwWeatherFormMapper {

    public List<OwWeatherForm> mapFacts(List<OwCurrentDto> list) {
        return list.stream().map(this::mapFact).toList();
    }

    public List<OwWeatherForm> mapDaily(List<OwDailyDto> list) {
        return list.stream().map(this::mapDaily).toList();
    }

    public List<OwMessageForm> mapMessages(List<OwMessageDto> list) {
        return list.stream().map(this::mapMessage).toList();
    }

    public OwWeatherForm mapFact(OwCurrentDto dto) {
        OwWeatherForm form = new OwWeatherForm();
        form.setTemp(mapTemperature(dto.getTemp()));
        form.setClouds(dto.getClouds());
        form.setDewPoint(dto.getDewPoint());
        form.setObsTime(dto.getDt());
        form.setFeelsLike(mapTemperature(dto.getFeelsLike()));
        form.setHumidity(dto.getHumidity());
        form.setPressureMm(dto.getPressure());
        form.setPrecipM1h(null);
        form.setUvi(dto.getUvi());
        form.setVisibility(dto.getVisibility());
        form.setWindDir(mapWindDeg(dto.getWindDeg()));
        form.setWindSpeed(dto.getWindSpeed());
        form.setWindGust(dto.getWindGust());
        form.setCondition(mapCondition(dto.getWeather()));
        //form.setIcon(dto.getIcon());
        form.setTempWater(null);
        return form;
    }

    private String mapWindDeg(Integer deg) {
        if ((deg >= 0) && (deg <= 22)) {
            return "C";
        }
        if ((deg >= 23) && (deg <= 67)) {
            return "CВ";
        }
        if ((deg >= 68) && (deg <= 112)) {
            return "В";
        }
        if ((deg >= 113) && (deg <= 157)) {
            return "ЮВ";
        }
        if ((deg >= 158) && (deg <= 202)) {
            return "Ю";
        }
        if ((deg >= 203) && (deg <= 247)) {
            return "ЮЗ";
        }
        if ((deg >= 248) && (deg <= 292)) {
            return "З";
        }
        if ((deg >= 293) && (deg <= 337)) {
            return "CЗ";
        }
        if ((deg >= 337) && (deg <= 360)) {
            return "C";
        }
        throw new IllegalStateException("deg");
    }

    private String mapCondition(List<OwWeatherDto> weather) {
        return weather.stream().map(OwWeatherDto::getDescription).reduce("", String::concat);
    }

    private String mapTemperature(Double temperatureD) {
        if (temperatureD == null) {
            return null;
        }
        int temperature = (int) Math.round(temperatureD);
        if (temperature > 0) {
            return "+" + temperature;
        }
        if (temperature < 0) {
            return String.valueOf(temperature);
        }
        return "0";
    }

    public OwWeatherForm mapDaily(OwDailyDto dto) {
        OwWeatherForm form = new OwWeatherForm();
        form.setTemp(mapTemperature(dto.getTemp().getDay()));
        form.setClouds(dto.getClouds());
        form.setDewPoint(dto.getDewPoint());
        form.setObsTime(dto.getDt());
        form.setFeelsLike(mapTemperature(dto.getFeelsLike().getDay()));
        form.setHumidity(dto.getHumidity());
        form.setPressureMm(dto.getPressure());
        form.setPrecipM1h(null);
        form.setUvi(dto.getUvi());
        form.setVisibility(dto.getVisibility());
        form.setWindDir(mapWindDeg(dto.getWindDeg()));
        form.setWindSpeed(dto.getWindSpeed());
        form.setWindGust(dto.getWindGust());
        form.setCondition(mapCondition(dto.getWeather()));
        //form.setIcon(dto.getIcon());
        form.setTempWater(null);
        return form;
    }

    public OwMessageForm mapMessage(OwMessageDto dto) {
        OwWeatherForm fact = mapFact(dto.getCurrent());
        List<OwWeatherForm> daily = mapDaily(dto.getDaily());
        return new OwMessageForm(fact, daily);
    }
}
