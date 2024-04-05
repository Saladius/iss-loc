package com.ff.issloc.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ff.issloc.dto.IssLocDto;
import com.ff.issloc.service.IssLocService;
import com.ff.issloc.service.WikipediaGeoSearchService;
import com.ff.issloc.wiki.response.WikiGeoSearchResponse;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class IssLocController {
	
	@Autowired
	private WikipediaGeoSearchService wikipediaGeoSearchService;
	
	@Autowired
	private IssLocService issLocService;
		
	 @GetMapping(path = "/")
	    public Map<String, List<IssLocDto>> getInterestingLocationNearIss(){
		  Map<String, List<IssLocDto>> response = new HashMap<>();
		 try {
	        List<IssLocDto> locations = issLocService.getAllLocationsNearIss();
	      
	        response.put("results", locations);
	        return response;
		 }catch (Exception e) {
//			System.out.println(e.getMessage());
			 e.printStackTrace();
//			 System.out.println("un problème est survenu");
			 response.put("results", new ArrayList<IssLocDto>());
		        return response;
		}
	    }
	 
	 
	 
	 
	 
	 

	 @GetMapping(path = "/test/rabat")
	    public Map<String, List<IssLocDto>> testRabat(){
		 List<IssLocDto> locations =  issLocService.filterAndProcessData(34.020882, -6.841650);
		 
		 Map<String, List<IssLocDto>> response = new HashMap<>();
	        response.put("results", locations);
	       return response;
	 }
}
