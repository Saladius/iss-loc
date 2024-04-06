package com.ff.issloc.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
@Data
public class IssLocClient {
	
	private final RestTemplate restTemplate = new RestTemplate();

	public String callExternalApiGetBody(String url){
		
		HttpEntity<?> requestEntity = getHeaders();
		
		ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

		log.info("Body : " + responseEntity.getBody());

		return responseEntity.getBody();
		
	}

	private HttpEntity<?> getHeaders() {
		final HttpHeaders headers= new HttpHeaders();
		headers.set("User-Agent", "Java HttpClient");
		return new HttpEntity<>("",headers);
	}
}
