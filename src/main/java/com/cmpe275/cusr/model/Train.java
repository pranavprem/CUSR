package com.cmpe275.cusr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table ( name = "train")
public class Train {
	
	@Column(name = "seats")
	private long seats;

	public long getSeats() {
		return seats;
	}

	public void setSeats(long seats) {
		this.seats = seats;
	}

}
