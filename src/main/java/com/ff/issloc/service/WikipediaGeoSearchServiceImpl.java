package com.ff.issloc.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ff.issloc.wiki.response.WikiGeoSearchResponse;

@Service
public class WikipediaGeoSearchServiceImpl implements WikipediaGeoSearchService{

	 private static final String BASE_URL = "https://fr.wikipedia.org/w/api.php";
	 
	 @Override
	 public WikiGeoSearchResponse fetchGeoSearch(double latitude, double longitude, int radius, int limit) {
		 try {
			   String gscoordParam = latitude + "%7C" + longitude;
	            String requestUri = BASE_URL + "?action=query&list=geosearch&gscoord=" 
	                + gscoordParam 
	                + "&gsradius=" + radius 
	                + "&gslimit=" + limit 
	                + "&format=json";
	            
		 System.out.println("requestUri: " + requestUri);
	        HttpClient client = HttpClient.newHttpClient();
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create(requestUri))
	                .header("User-Agent", "Java HttpClient") // It's important to set a User-Agent
	                .build();

	
	            // Execute the HTTP request
	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

	            System.out.println("Body : " + response.body());
	            // Use Jackson to deserialize the JSON response
	            ObjectMapper mapper = new ObjectMapper();
	            return mapper.readValue(response.body(), WikiGeoSearchResponse.class);

	        } catch (Exception e) {
	            e.printStackTrace();
	            return null; // In real scenarios, consider a better error handling strategy
	        }
	    }
}
