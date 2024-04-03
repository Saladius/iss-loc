package com.ff.issloc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OpenApiIssLocDto {
	private String message;
	private long timestamp;
	@JsonProperty("iss_position")
	private IssPosition issPosition;
}
