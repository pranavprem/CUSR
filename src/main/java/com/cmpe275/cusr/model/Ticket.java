package com.cmpe275.cusr.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table (name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @OneToMany(mappedBy = "ticket", fetch = FetchType.EAGER)
    private List<Train> trains;
    @Column(name = "cost")
    private long cost;
    @Transient
    private List<Passenger> passenger;
    @Column(name = "userId")
    private long userId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public List<Passenger> getPassenger() {
        return passenger;
    }

    public void setPassenger(List<Passenger> passenger) {
        this.passenger = passenger;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public List<Train> getTrains() {
        return trains;
    }

    public void setTrains(List<Train> trains) {
        this.trains = trains;
    }
}
