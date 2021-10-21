/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ow.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "ow_alert")
public class OwAlert implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long alertId;
    /**
     * Name of the alert source
     */
    private String senderName;
    /**
     * Alert event name
     */
    @NotNull
    private String event;
    /**
     * Date and time of the start of the alert, Unix, UTC
     */
    @NotNull
    private OffsetDateTime start;
    /**
     * Date and time of the end of the alert, Unix, UTC
     */
    @NotNull
    private OffsetDateTime end;
    /**
     * Description of the alert
     */
    private String description;

    @ManyToOne
    @JoinColumn(name = "message_id", referencedColumnName = "message_id", nullable = false, updatable = false)
    private OwMessage message;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OwAlert)) {
            return false;
        }
        OwAlert other = (OwAlert) o;
        return alertId != null &&
                alertId.equals(other.getAlertId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(alertId);
    }
}
