package com.cmpe275.cusr.controller;

import com.cmpe275.cusr.model.Ticket;
import com.cmpe275.cusr.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    ResponseEntity getAllTicket(@PathVariable("id") long userId) {
        return ResponseEntity.ok(ticketService.getTickets(userId));
    }

    @PostMapping(value = "/{id}/ticket/book")
    ResponseEntity bookTicket(@RequestBody Ticket ticket) {
        return ResponseEntity.ok("Ticket Booked Successfully");
    }

   /* @PutMapping(value = "/{id}/ticket/cancel")
    ResponseEntity cancelTicket(@RequestAttribute)*/

}
