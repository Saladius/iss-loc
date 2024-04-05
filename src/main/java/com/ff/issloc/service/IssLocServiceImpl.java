package com.ff.issloc.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ff.issloc.dto.IssLocDto;
import com.ff.issloc.dto.OpenApiIssLocDto;
import com.ff.issloc.wiki.response.WikiGeoSearch;
import com.ff.util.GeoUtils;

@Service
public class IssLocServiceImpl implements IssLocService {

	@Value("${issloc.baseurl}")
	private String baseUrlIssLoc = "";
	@Value("${issloc.radius}")
	private int radius;
	@Value("${issloc.limit}")
	private int limit;

	@Autowired
	private WikipediaGeoSearchService wikipediaGeoSearchService;

	@Autowired
	private NationByLocService nationByLocService;

	@Override
	public List<IssLocDto> getAllLocationsNearIss() throws IOException, InterruptedException {
		OpenApiIssLocDto issLoc = getLocationNearIss();
		double latitude = Double.parseDouble(issLoc.getIssPosition().getLatitude());
		double longitude = Double.parseDouble(issLoc.getIssPosition().getLongitude());

		return filterAndProcessData(latitude, longitude);
	}

	@Override
	public List<IssLocDto> filterAndProcessData(double latitude, double longitude) {
		List<IssLocDto> result = new ArrayList<IssLocDto>();
		List<WikiGeoSearch> response = wikipediaGeoSearchService.fetchGeoSearch(latitude, longitude, radius, limit)
				.getQuery().getGeoSearchList();

		filterListResult(response, latitude, longitude).stream()
				.forEach(res -> result.add(IssLocDto.builder()
						.country(nationByLocService.getCountryByLocalisation(res.getLat(), res.getLon()))
						.title(res.getTitle()).latitude(res.getLat()).longitude(res.getLon()).build()));
		return result;
	}

	@Override
	public OpenApiIssLocDto getLocationNearIss() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(baseUrlIssLoc))
				.header("User-Agent", "Java HttpClient") // It's important to set a User-Agent
				.build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		System.out.println("Body : " + response.body());

		ObjectMapper mapper = new ObjectMapper();

		return mapper.readValue(response.body(), OpenApiIssLocDto.class);
	}

	@Override
	public List<WikiGeoSearch> filterListResult(List<WikiGeoSearch> results, double latitude, double longitude) {

		// TODO FF il faut maintenant créer les unit-test
		return results.stream()
                .sorted((wis1, wis2) -> {
                    double dist1 = GeoUtils.haversine(latitude, longitude, wis1.getLat(), wis1.getLon());
                    double dist2 = GeoUtils.haversine(latitude, longitude, wis2.getLat(), wis2.getLon());
                    return Double.compare(dist1, dist2);
                })
                .limit(10)
                .collect(Collectors.toList());
	}
}
