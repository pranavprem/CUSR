package com.cmpe275.cusr.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmpe275.cusr.util.Search;

import com.cmpe275.cusr.util.SearchResult;


@Service
public class SearchServiceImpl implements SearchService {
	
	@Autowired
	TicketService ticketService;


	@Override
	public List<SearchResult> search(Character from, Character to, String time) {
		List<SearchResult> returnlist = new ArrayList<SearchResult>();
		boolean flag;
		DateFormat df = new SimpleDateFormat("HH:mm");
		Date timeobj = null;
		try {
			timeobj= df.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SearchResult result;
		for(int i=0;i<120;i+=15) {
			flag=true;
			timeobj.setMinutes(timeobj.getMinutes()+15);
			if(timeobj.getMinutes()!=0) {
				returnlist.add(this.searchregular(from, to, df.format(timeobj)));
				
			}
			result = this.searchmix(from, to, df.format(timeobj));
			for(SearchResult searchResult:returnlist) {
				if(searchResult.getArrivalTime().equals(result.getArrivalTime())) {
					flag=false;
				}
			}
			if(flag) {
				returnlist.add(result);
			}
		}
		
		Set<SearchResult> hs = new HashSet<SearchResult>();
		hs.addAll(returnlist);
		returnlist.clear();
		returnlist.addAll(hs);
		
		Collections.sort(returnlist);
		
		return returnlist;
	}
	
	
	public SearchResult searchmix (Character from, Character to, String time){
		Search search = new Search();
		String trains = null;
		try {
			trains = search.searchmix(from, to, time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String arrivaltime="";
		System.out.println(trains);
		System.out.println(trains.split("---")[0].split(",")[2]);
		if(trains.split("---")[0].split(",")[2].equals("null")) {
			arrivaltime = search.findtime(trains.split("---")[0].split(",")[1], to);
		}else {
			arrivaltime = search.findtime(trains.split("---")[0].split(",")[2], to);
		}
			
		List<SearchResult> returnlist = new ArrayList<SearchResult>();
		SearchResult result = new SearchResult();
		result.setArrivalTime(arrivaltime);
		List<String> trainsList = new LinkedList<String>(Arrays.asList(trains.split("---")[0].split(",")));
		List<String> route = new LinkedList<String>(Arrays.asList(trains.split("---")[1].split(",")));
		
		if(trainsList.get(2).equals("null")) {
			trainsList.remove(2);
			route.remove(2);
		}
		if(trainsList.get(0).equals("null")) {
			trainsList.remove(0);
			route.remove(0);
		}
		int fare = 0;
		int seats = 1000;
		for (String train : trainsList) {
			fare += cost(route.get(0), route.get(route.size() -1),1,train);
			try {
				if(ticketService.getSeatAvailable(train)<seats)seats=(int) ticketService.getSeatAvailable(train);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		result.setTrains(trainsList);
		result.setRoute(route);
		result.setCost(fare);
		returnlist.add(result);
		if(seats==0) {
			return null;
		}
		result.setSeats(seats);
		return result;
	}

	public int cost(String o, String d, int pcount, String trainId)
	{
		char co = o.charAt(0);
		char cd = d.charAt(0);
		int diff = Math.abs(co-cd);
		int cost = diff/5;
		int trainfare =(trainId.substring(4,6).equals("00")) ? 2 : 1;

		return (diff%5 > 0 ? cost : cost) * pcount * trainfare;
	}
	
	public SearchResult searchregular(Character from, Character to, String time) {
		Search search = new Search();
		String trains = null;
		trains = search.search(from, to, time);
		
		String arrivaltime="";
		arrivaltime = search.findtime(trains, to);
			
		List<SearchResult> returnlist = new ArrayList<SearchResult>();
		SearchResult result = new SearchResult();
		result.setArrivalTime(arrivaltime);
		List<String> trainsList = new LinkedList<String>(Arrays.asList(trains.split(",")));
		List<String> route = new ArrayList<String>();
		route.add(""+from);
		route.add(""+to);
		result.setTrains(trainsList);
		result.setRoute(route);
		int fare = 0;
		int seats = 1000;
		for (String train : trainsList) {
			fare += cost(route.get(0), route.get(route.size() -1),1,train);
			try {
				if(ticketService.getSeatAvailable(train)<seats)seats=(int) ticketService.getSeatAvailable(train);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		result.setCost(fare);
		returnlist.add(result);
		if(seats==0) {
			return null;
		}
		result.setSeats(seats);
		return result;
	}


	@Override
	public List<SearchResult> searchround(Character from, Character to, String time, String roundTrip, String time2) {
		List<SearchResult> returnlist = new ArrayList<SearchResult>();
		boolean flag;
		DateFormat df = new SimpleDateFormat("HH:mm");
		Date timeobj = null, timeobj2 = null;
		try {
			timeobj= df.parse(time);
			timeobj2= df.parse(time2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SearchResult result, result2;
		for(int i=0;i<120;i+=15) {
			flag=true;
			timeobj.setMinutes(timeobj.getMinutes()+15);
			timeobj2.setMinutes(timeobj2.getMinutes()+15);
			if(timeobj.getMinutes()!=0) {
				result = this.searchregular(from, to, df.format(timeobj));
				result2 = this.searchregular(to, from, df.format(timeobj2));
				result.setArrivalTime2(result2.getArrivalTime());
				result.getTrains().addAll(result2.getTrains());
				result.getRoute().addAll(result2.getRoute());
				result.setCost(result.getCost()+result2.getCost());
				returnlist.add(result);
			}
			result = this.searchmix(from, to, df.format(timeobj));
			result2 = this.searchmix(to, from, df.format(timeobj2));
			result.setArrivalTime2(result2.getArrivalTime());
			result.getTrains().addAll(result2.getTrains());
			result.getRoute().addAll(result2.getRoute());
			result.setCost(result.getCost()+result2.getCost());
			for(SearchResult searchResult:returnlist) {
				if(searchResult.getArrivalTime().equals(result.getArrivalTime()) && searchResult.getArrivalTime2().equals(result.getArrivalTime2())) {
					flag=false;
				}
			}
			if(flag) {
				returnlist.add(result);
			}
		}
		
		Set<SearchResult> hs = new HashSet<SearchResult>();
		hs.addAll(returnlist);
		returnlist.clear();
		returnlist.addAll(hs);
		
		Collections.sort(returnlist);
		
		return returnlist;
	}

}
