package com.ff.issloc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ff.issloc.client.IssLocClient;
import com.ff.issloc.wiki.response.WikiGeoSearchResponse;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Data
public class WikipediaGeoSearchServiceImpl implements WikipediaGeoSearchService {

	@Value("${issloc.baseurl.wikipedia}")
	public String baseUrlWiki;
	
	@Autowired
	private IssLocClient client;
	

	@Override
	public WikiGeoSearchResponse fetchGeoSearch(double latitude, double longitude, int radius, int limit) {
		try {
			log.info("we're trying to get all interesting location near iss");
			String gscoordParam = latitude + "|" + longitude;
			String requestUri = baseUrlWiki + gscoordParam + "&gsradius=" + radius + "&gslimit=" + limit
					+ "&format=json";

			log.info("requestUri: " + requestUri);
			
			
			String responseBody = client.callExternalApiGetBody(requestUri);
					log.info("Body Wiki search: " + responseBody);
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(responseBody, WikiGeoSearchResponse.class);

		} catch (Exception e) {
			log.info(e.getMessage());
			return null; 
		}
	}
}
