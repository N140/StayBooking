package com.example.staybooking.model;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;


@Embeddable
public class StayAvailabilityKey implements Serializable {
    private long stay_id;
    private LocalDate date;
    private static final long serialVersionUID=1L;

    public StayAvailabilityKey(){}

    public StayAvailabilityKey(long stay_id, LocalDate date) {
        this.stay_id = stay_id;
        this.date = date;
    }

    public long getId() {
        return stay_id;
    }

    public void setId(long stay_id) {
        this.stay_id = stay_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StayAvailabilityKey that = (StayAvailabilityKey) o;
        return stay_id == that.stay_id && date.equals(that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stay_id, date);
    }
}
