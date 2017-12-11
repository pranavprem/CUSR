package com.cmpe275.cusr.controller;


import com.cmpe275.cusr.service.SearchService;
import com.cmpe275.cusr.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SearchController {

	@Autowired
	SearchService searchService;

	@GetMapping(value = "/search/{from}/{to}/{time}")
	ResponseEntity search(@PathVariable("from") Character from, @PathVariable("to") Character to,
			@PathVariable("time") String time) {
		return ResponseEntity.ok(this.searchService.search(from, to, time));
		
	}

}
