package com.ff.issloc.wiki.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WikiGeoSearchResponse {
	
	private String batchcomplete;
    @JsonProperty("query")
    private WikiQuery query;
    
}