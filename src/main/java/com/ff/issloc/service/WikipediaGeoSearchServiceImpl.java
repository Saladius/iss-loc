package com.ff.issloc.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ff.issloc.wiki.response.WikiGeoSearchResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WikipediaGeoSearchServiceImpl implements WikipediaGeoSearchService {

	@Value("${issloc.baseurl.wikipedia}")
	public String baseUrlWiki;

	@Override
	public WikiGeoSearchResponse fetchGeoSearch(double latitude, double longitude, int radius, int limit) {
		try {
			String gscoordParam = latitude + "%7C" + longitude;
			String requestUri = baseUrlWiki + gscoordParam + "&gsradius=" + radius + "&gslimit=" + limit
					+ "&format=json";

			log.info("requestUri: " + requestUri);
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(requestUri))
					.header("User-Agent", "Java HttpClient") // It's important to set a User-Agent
					.build();

			
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			log.info("Body Wiki search: " + response.body());
			
			
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(response.body(), WikiGeoSearchResponse.class);

		} catch (Exception e) {
			log.info(e.getMessage());
			return null; 
		}
	}
}
