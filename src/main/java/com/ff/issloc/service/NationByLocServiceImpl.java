package com.ff.issloc.service;

import org.springframework.stereotype.Service;

import io.github.coordinates2country.Coordinates2Country;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NationByLocServiceImpl implements NationByLocService {


	
	@Override
	public String getCountryByLocalisation(double latitude, double longitude) {

		return Coordinates2Country.country(latitude, longitude);
	}

	
}
