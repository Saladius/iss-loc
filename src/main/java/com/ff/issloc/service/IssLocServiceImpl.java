package com.ff.issloc.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ff.issloc.dto.IssLocDto;
import com.ff.issloc.dto.OpenApiIssLocDto;
import com.ff.issloc.wiki.response.WikiGeoSearch;

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
		List<IssLocDto> result = new ArrayList<IssLocDto>();
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
	public List<WikiGeoSearch> filterListResult(List<WikiGeoSearch> result, double latitude, double longitude) {

		// TODO FF il faut maintenant filtrer just les 10 résultats les plus proches
//		il faut aussi changer les statics dans les services en properties
//		il ne faut pas oublier non plus de rajouter le service qui récupére la longitude et latitude pour nous donner le pays.
//		sans oublier biensur les unit-test
		return result;
	}
}
