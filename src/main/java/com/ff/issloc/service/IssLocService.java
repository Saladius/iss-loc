package com.ff.issloc.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ff.issloc.dto.IssLocDto;

@Service
public interface IssLocService {

	public List<IssLocDto> getAllLocationsNearIss() throws IOException, InterruptedException;
}
