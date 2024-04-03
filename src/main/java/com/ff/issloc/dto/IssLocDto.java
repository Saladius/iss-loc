package com.ff.issloc.dto;

import com.fasterxml.jackson.annotation.JsonSetter;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IssLocDto {

	private String title;
	private double latitude;
	private double longitude;
	private String country;
	
	 	@JsonSetter("latitude")
	    public void setLatitude(String latitude) {
	        this.latitude = Double.parseDouble(latitude);
	    }

	    @JsonSetter("longitude")
	    public void setLongitude(String longitude) {
	        this.longitude = Double.parseDouble(longitude);
	    }
}
