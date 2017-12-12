package com.cmpe275.cusr.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cmpe275.cusr.util.Search;

import com.cmpe275.cusr.util.SearchResult;


@Service
public class SearchServiceImpl implements SearchService {


	@Override
	public List<SearchResult> search(Character from, Character to, String time) {
		Search search = new Search();
		String trains = null;
		try {
			trains = search.searchmix(from, to, time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String arrivaltime = search.findtime(trains.split(",")[2], to);
		List<SearchResult> returnlist = new ArrayList<SearchResult>();
		SearchResult result = new SearchResult();
		result.setArrivalTime(arrivaltime);
		List<String> trainsList = Arrays.asList(trains.split(","));
		result.setTrains(trainsList);
		returnlist.add(result);
		return returnlist;
	}

}
