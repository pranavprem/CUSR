package com.cmpe275.cusr.controller;

import com.cmpe275.cusr.model.Ticket;
import com.cmpe275.cusr.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping(value = "/home")
    @ResponseBody
    public String hello() {
        return "Hello";
    }

    @GetMapping(value = "/{id}/ticket")
    ResponseEntity getTickets(@PathVariable("id") long userId) {
        return ResponseEntity.ok(ticketService.getTickets(userId));
    }

    @GetMapping(value = "cancel")
    ResponseEntity cancelTrain(@RequestParam("trainId") String trainId) {

        return ResponseEntity.ok("train cancelled");
    }

    @GetMapping(value = "/reset")
    ResponseEntity resetData(@RequestParam("seats") String seats) {
            ticketService.resetData(Long.parseLong(seats));
        return ResponseEntity.ok("Data has been reset");
    }

    @GetMapping(value = "/seats")
    ResponseEntity getSeats(@RequestParam("trainId") String trainId) {
        Map<String,Long> response = new HashMap<>();
        response.put("seats" ,ticketService.getSeatAvailable(trainId));
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/ticket")
    ResponseEntity getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTicket());
    }

    @PostMapping(value = "/{id}/ticket/book")
    ResponseEntity bookTicket(@RequestBody Ticket ticket) {
        return ResponseEntity.ok(ticketService.addTicket(ticket));
    }

    @DeleteMapping(value = "{id}/ticket/{tid}")
    ResponseEntity cancelTicket(@PathVariable("tid") long ticketId) {

        try
        {
            return ResponseEntity.ok(ticketService.deleteTicket(ticketId));
        }
        catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("It's too late");
        }

    }

}
