package com.cmpe275.cusr.controller;


import ch.qos.logback.core.net.SyslogOutputStream;
import com.cmpe275.cusr.service.SearchService;
import com.cmpe275.cusr.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class SearchController {



	@Autowired
	SearchService searchService;

	
	
	@GetMapping(value = "/search/{from}/{to}/{time}/{returnTrip}/{time2}")
	ResponseEntity search(@PathVariable("from") Character from, @PathVariable("to") Character to,
			@PathVariable("time") String time, @PathVariable("returnTrip") String returnTrip, @PathVariable("time2") String time2) {
		System.out.println(from+" "+to+" "+time);
		
		if(returnTrip.equals("No")) {
			return ResponseEntity.ok(this.searchService.search(from, to, time));
		}else {
			return ResponseEntity.ok(this.searchService.searchround(from, to, time, returnTrip, time2));
		}
		
		
	}
	

}
