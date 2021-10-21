/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@EqualsAndHashCode
public class OwAlertDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Name of the alert source
     */
    @JsonProperty("sender_name")
    private String senderName;
    /**
     * Alert event name
     */
    private String event;
    /**
     * Date and time of the start of the alert, Unix, UTC
     */
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Instant start;
    /**
     * Date and time of the end of the alert, Unix, UTC
     */
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Instant end;
    /**
     * Description of the alert
     */
    private String description;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
