package com.ff.issloc.service;

import org.springframework.stereotype.Service;

import io.github.coordinates2country.Coordinates2Country;

@Service
public class NationByLocServiceImpl implements NationByLocService {


	
	@Override
	public String getCountryByLocalisation(double latitude, double longitude) {
		//Tried at first to use the opencage api.
		// then i tried using the Reverse Country Code Library
		//But i obtained the best result with the Coordinates2Country library
		return Coordinates2Country.country(latitude, longitude);
	}

	
}
