package com.cmpe275.cusr.controller;

import com.cmpe275.cusr.model.Ticket;
import com.cmpe275.cusr.model.User;
import com.cmpe275.cusr.service.EmailService;
import com.cmpe275.cusr.service.TicketService;
import com.cmpe275.cusr.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
	private UserService userService;
	
	@Autowired
	private EmailService emailService;
	
    @GetMapping(value = "/home")
    @ResponseBody
    public String hello() {
        return "Hello";
    }

    @GetMapping(value = "/{id}/ticket")
    ResponseEntity getTickets(@PathVariable("id") long userId) {
    	try {
    		
    		List<Ticket> ticket = ticketService.getTickets(userId);
    		
    		if (ticket == null) {
    			
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No Ticket Found!");
    			
    		}else {
    			
        		return ResponseEntity.ok(ticketService.getTickets(userId));

    		}
    		
    		
    	} catch (Exception e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Get Tickets Failed!");

		}	
        
    }

    @GetMapping(value = "/{id}/ticket/{tid}")
    ResponseEntity getTicket(@PathVariable("id") long userId, @PathVariable("tid") long ticketId){
        
    	try {
    		
    		Ticket ticket = ticketService.getTicketDetail(ticketId);
    		
    		if (ticket == null) {
    			
    			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Get Ticket not found!");

    			
    		}else {
    			
            	return ResponseEntity.ok(ticket);

    		}
    		    		
    	}catch(Exception e) {
    		
    		e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Get Ticket Failed!");

    	}
    	
    }

    @GetMapping(value = "cancel")
    ResponseEntity cancelTrain(@RequestParam("trainId") String trainId) {
    	 	
        return ResponseEntity.ok("Train Cancelled");
        
    }

    @GetMapping(value = "/reset")
    ResponseEntity resetData(@RequestParam("seats") String seats) {
        
    	try {
    		
    		ticketService.resetData(Long.parseLong(seats));
        	
            return ResponseEntity.ok("Data has been reset");    		
    		
    	}catch(Exception e) {
    		
    		e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ticket data reset failed.");

    	}
    	
        
    }

    @GetMapping(value = "/seats")
    ResponseEntity getSeats(@RequestParam("trainId") String trainId) {
    	
    	try {
    		
    		Map<String,Long> response = new HashMap<>();    		
            response.put("seats" ,ticketService.getSeatAvailable(trainId));
            return ResponseEntity.ok(response);
            
    	}catch(Exception e) {
    		
    		e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ticket get seats failed.");
    		
    	}
        
    }

    @GetMapping(value = "/ticket")
    ResponseEntity getAllTickets() {
    	
    	try {
    	
    		List<Ticket> tickets = ticketService.getAllTicket();
    	
    		if (tickets == null) {
    			
    			return ResponseEntity.ok(tickets);
    		
    		}else {
    		
    			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Get All Tickets Failed- None Found.");

    		}
    		
    	}catch(Exception e) {
    		
    		e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Get All Tickets Failed.");
    		
    	}
    	
    }

    @PostMapping(value = "/{id}/ticket/book")
    ResponseEntity bookTicket(@PathVariable("id") long id,
    		@RequestBody Ticket ticket) {
    	try {
    		
    		User user = userService.getUser(id);
    		String email = user.getEmail();
    		String subject = "Ticket Booking Confirmation";
    		String body = ticketService.getEmailBody(ticket);    		
    		
    		emailService.send("california.usr@gmail.com", email, subject, body);    		
    		    		
            return ResponseEntity.ok(ticketService.addTicket(ticket));

    	} catch (Exception e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Book Ticket Failed.");

		}	
    }

    @DeleteMapping(value = "{id}/ticket/{tid}")
    ResponseEntity cancelTicket(@PathVariable("id") long id,
    		@PathVariable("tid") long ticketId) {

        try
        {
        	User user = userService.getUser(id);
    		String email = user.getEmail();
    		String subject = "Ticket Cancellation";
    		String body = "<html><body>Ticket has been cancelled.</body></html>";   		
    		
    		emailService.send("california.usr@gmail.com", email, subject, body); 
    		
            return ResponseEntity.ok(ticketService.deleteTicket(ticketId));
        }
        catch (IllegalStateException e) {
        	
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("It's too late");
            
        }
        catch(Exception e) {
        	
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cancel Ticket Failed.");
        	
        }

    }

}
