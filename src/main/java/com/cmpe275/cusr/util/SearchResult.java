package com.cmpe275.cusr.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchResult implements Comparable<SearchResult>{
    List<String> trains;
    String arrivalTime;
    String arrivalTime2;
    int cost;
    List<String> route;
    int seats;

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

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public List<String> getRoute() {
		return route;
	}

	public void setRoute(List<String> route) {
		this.route = route;
	}
	
	public String getArrivalTime2() {
		return arrivalTime2;
	}

	public void setArrivalTime2(String arrivalTime2) {
		this.arrivalTime2 = arrivalTime2;
	}
	
	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	@Override
	public int compareTo(SearchResult o) {
		DateFormat df = new SimpleDateFormat("HH:mm");
		Date date1 = null, date2 = null;
		
		try {
			date1=df.parse(this.arrivalTime);
			date2=df.parse(o.arrivalTime);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return date1.compareTo(date2);
		
	}
    
    
}