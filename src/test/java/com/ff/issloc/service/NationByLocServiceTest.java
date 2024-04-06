package com.ff.issloc.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NationByLocServiceTest {
	@Autowired
	private NationByLocService nationByLocService;
	 @Test
	    void testFetchCountryMauritania() {
		 String country = nationByLocService.getCountryByLocalisation(18.891360, -11.255679);
		 
		 assertEquals("Mauritania", country);
	 }
	 
	 
	 @Test
	    void testFetchCountryBelgium() {
		 String country = nationByLocService.getCountryByLocalisation(50.858876, 4.936098);
		 
		 assertEquals("Belgium", country);
	 }
	 
	 @Test
	    void testFetchCountryColombia() {
		 String country = nationByLocService.getCountryByLocalisation(2.439517, -75.183247);
		 
		 assertEquals("Colombia", country);
	 }

}
