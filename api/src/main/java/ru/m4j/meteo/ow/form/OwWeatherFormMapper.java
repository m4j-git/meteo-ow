/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.form;

import java.util.List;

import org.springframework.stereotype.Component;

import ru.m4j.meteo.ow.model.OwCurrentDto;
import ru.m4j.meteo.ow.model.OwDailyDto;
import ru.m4j.meteo.ow.model.OwMessageDto;

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
        form.setTemp((int) Math.round(dto.getTemp()));
        form.setClouds(dto.getClouds());
        form.setDewPoint(dto.getDewPoint());
        form.setObsTime(dto.getDt());
        form.setFeelsLike((int) Math.round(dto.getFeelsLike()));
        form.setHumidity(dto.getHumidity());
        form.setPressure(dto.getPressure());
        form.setPrecipM1h(null);
        form.setUvi(dto.getUvi());
        form.setVisibility(dto.getVisibility());
        form.setWindDeg(dto.getWindDeg());
        form.setWindSpeed(dto.getWindSpeed());
        form.setWindGust(dto.getWindGust());
        // form.setWeather(dto.getW);
        //form.setIcon(dto.getIcon());
        form.setTempWater(null);
        return form;
    }

    public OwWeatherForm mapDaily(OwDailyDto dto) {
        OwWeatherForm form = new OwWeatherForm();
        form.setTemp((int) Math.round(dto.getTemp().getDay()));
        form.setClouds(dto.getClouds());
        form.setDewPoint(dto.getDewPoint());
        form.setObsTime(dto.getDt());
        form.setFeelsLike((int) Math.round(dto.getFeelsLike().getDay()));
        form.setHumidity(dto.getHumidity());
        form.setPressure(dto.getPressure());
        form.setPrecipM1h(null);
        form.setUvi(dto.getUvi());
        form.setVisibility(dto.getVisibility());
        form.setWindDeg(dto.getWindDeg());
        form.setWindSpeed(dto.getWindSpeed());
        form.setWindGust(dto.getWindGust());
        // form.setWeather(dto.getW);
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
