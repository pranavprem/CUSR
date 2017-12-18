package com.cmpe275.cusr.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table ( name = "train")
public class Train {

	@Id
	@Column(name = "id")
	private String train;
	@Column(name = "seats")
	private long seats;
	@Column(name = "departureStation")
	private String departureStation;
	@Column(name = "arrivalStation")
	private String arrivalStation;
	@Column(name = "departureTime")
	private String departureTime;
	@Column(name = "arrivalTime")
	private String arrivalTime;
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinTable(name = "ticket_id")
	private Ticket ticket;

	public long getSeats() {
		return seats;
	}

	public void setSeats(long seats) {
		this.seats = seats;
	}

	public String getDepartureStation() {
		return departureStation;
	}

	public void setDepartureStation(String departureStation) {
		this.departureStation = departureStation;
	}

	public String getArrivalStation() {
		return arrivalStation;
	}

	public void setArrivalStation(String arrivalStation) {
		this.arrivalStation = arrivalStation;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public String getTrain() {
		return train;
	}

	public void setTrain(String train) {
		this.train = train;
	}
}
