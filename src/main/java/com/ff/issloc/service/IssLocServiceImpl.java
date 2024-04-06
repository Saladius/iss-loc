package com.ff.issloc.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ff.issloc.client.IssLocClient;
import com.ff.issloc.dto.IssLocDto;
import com.ff.issloc.dto.OpenApiIssLocDto;
import com.ff.issloc.wiki.response.WikiGeoSearch;
import com.ff.util.GeoUtils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Data
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
	
	@Autowired
	private IssLocClient client;

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
		log.info("we try to get the location of ISS");
		
		String responseBody = client.callExternalApiGetBody(baseUrlIssLoc);

		ObjectMapper mapper = new ObjectMapper();

		return mapper.readValue(responseBody, OpenApiIssLocDto.class);
	}

	@Override
	public List<WikiGeoSearch> filterListResult(List<WikiGeoSearch> results, double latitude, double longitude) {

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
