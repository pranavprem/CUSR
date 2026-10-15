package com.cmpe275.cusr.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.cmpe275.cusr.model.Passenger;
import com.cmpe275.cusr.model.Ticket;
import com.cmpe275.cusr.model.Train;
import java.util.List;
import org.junit.jupiter.api.Test;

class EmailBodyTest {

    @Test
    void confirmationRendersBothStoredTimeShapes() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setCost(12);
        Passenger passenger = new Passenger();
        passenger.setName("Ada");
        ticket.setPassenger(List.of(passenger));

        Train train = new Train();
        train.setTrain("SB235012/20/2099");
        train.setDepartureStation("A");
        train.setArrivalStation("C");
        train.setDepartureTime("235012/20/2099");
        train.setArrivalTime("23:5512/20/2099");
        ticket.setTrains(List.of(train));

        String body = new TicketServiceImpl().getEmailBody(ticket);

        assertTrue(body.contains("Ada"));
        assertTrue(body.contains("$12"));
        assertTrue(body.contains("23:50"));
        assertTrue(body.contains("23:55"));
    }
}
