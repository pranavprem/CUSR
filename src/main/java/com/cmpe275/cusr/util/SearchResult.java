package com.cmpe275.cusr.util;

import java.util.ArrayList;
import java.util.List;

public class SearchResult{
    List<String> trains;
    String arrivalTime;



    public List<String> getTrains() {
        return trains;
    }

    public void setTrains(List<String> trains) {
        this.trains = trains;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}