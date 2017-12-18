package com.cmpe275.cusr.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class TrainKey implements Serializable {

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "date", nullable = false)
    private String date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
