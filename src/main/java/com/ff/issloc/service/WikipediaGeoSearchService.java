package com.ff.issloc.service;

import org.springframework.stereotype.Service;

import com.ff.issloc.wiki.response.WikiGeoSearchResponse;

@Service
public interface WikipediaGeoSearchService {

	public WikiGeoSearchResponse fetchGeoSearch(double latitude, double longitude, int radius, int limit);
}
