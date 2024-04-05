package com.ff.issloc.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ff.issloc.dto.IssLocDto;
import com.ff.issloc.dto.OpenApiIssLocDto;
import com.ff.issloc.wiki.response.WikiGeoSearch;

@Service
public interface IssLocService {

	public List<IssLocDto> getAllLocationsNearIss() throws IOException, InterruptedException;
	public List<IssLocDto> filterAndProcessData(double latitude, double longitude);
	public OpenApiIssLocDto getLocationNearIss() throws IOException, InterruptedException;
	public List<WikiGeoSearch> filterListResult(List<WikiGeoSearch> result, double latitude, double longitude);
}
