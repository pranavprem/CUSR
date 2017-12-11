package com.cmpe275.cusr.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Passenger {

    @Column(name = "name")
    private String name;
    @Column(name = "seatNumber")
    private long seatNumber;
}
