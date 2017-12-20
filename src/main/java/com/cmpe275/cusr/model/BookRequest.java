package com.cmpe275.cusr.model;

import java.util.List;

public class BookRequest {

    private long userId;
    private List<String> trains;
    private List<String> route;
    private int cost;
    private String arrival1;
    private String arrival2;
    private List<String> passengers;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public List<String> getTrains() {
        return trains;
    }

    public void setTrains(List<String> trains) {
        this.trains = trains;
    }

    public List<String> getRoute() {
        return route;
    }

    public void setRoute(List<String> route) {
        this.route = route;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getArrival1() {
        return arrival1;
    }

    public void setArrival1(String arrival1) {
        this.arrival1 = arrival1;
    }

    public String getArrival2() {
        return arrival2;
    }

    public void setArrival2(String arrival2) {
        this.arrival2 = arrival2;
    }

    public List<String> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<String> passengers) {
        this.passengers = passengers;
    }
}
