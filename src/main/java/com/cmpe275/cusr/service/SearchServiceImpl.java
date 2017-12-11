package com.cmpe275.cusr.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cmpe275.cusr.util.Search;


@Service
public class SearchServiceImpl implements SearchService {

	
	@Override
	public List<String> search(Character from, Character to, String time) {
		Search search = new Search();
		String trains = search.search(from, to, time);
		String arrivaltime = search.findtime(trains.split(",")[2], to);
		List<String> returnlist = new ArrayList<String>();
		returnlist.add(trains);
		returnlist.add(arrivaltime);
		return returnlist;
	}

}
