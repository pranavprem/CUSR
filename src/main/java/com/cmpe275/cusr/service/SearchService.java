package com.cmpe275.cusr.service;

import com.cmpe275.cusr.util.SearchResult;

import java.util.List;

public interface SearchService {
	List<SearchResult> search(Character from, Character to, String time);
}
