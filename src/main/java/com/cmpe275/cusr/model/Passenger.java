package com.cmpe275.cusr.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Passenger {

    @Column(name = "name")
    private String name;
    @Column(name = "seatNumber")
    private long seatNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(long seatNumber) {
        this.seatNumber = seatNumber;
    }
}
