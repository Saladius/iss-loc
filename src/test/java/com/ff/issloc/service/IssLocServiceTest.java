package com.ff.issloc.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import com.ff.issloc.client.IssLocClient;
import com.ff.issloc.dto.IssLocDto;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class IssLocServiceTest {



	@Mock
	private NationByLocService nationByLocService;

    @InjectMocks
    @Autowired
    private WikipediaGeoSearchServiceImpl wikipediaGeoSearchService;
	@Mock
	private IssLocClient client;

	@InjectMocks
	@Autowired
	private IssLocServiceImpl service;

	@Value("${issloc.baseurl}")
	private String baseUrlIssLoc = "";

	@Value("${issloc.baseurl.wikipedia}")
	private String baseUrlWiki;

	@Value("${issloc.radius}")
	private int radius;
	@Value("${issloc.limit}")
	private int limit;

	@Test
	void testGetAllLocationsNearIss() throws Exception {

		double latitude = 49.887411;
		double longitude = -97.183203;

		String issLocResponse = loadJsonFromFile("issLocWinnipeg.json");
		when(client.callExternalApiGetBody(baseUrlIssLoc)).thenReturn(issLocResponse);



		String gscoordParam = latitude + "|" + longitude;
		String fullUrl = baseUrlWiki + gscoordParam + "&gsradius=" + radius + "&gslimit=" + limit + "&format=json";

		String mockResponse = new String(
				Files.readAllBytes(Paths.get(new ClassPathResource("wikiGeoResponseRabat.json").getURI())));
		when(client.callExternalApiGetBody(fullUrl)).thenReturn(mockResponse);

		List<IssLocDto> result = service.getAllLocationsNearIss();

		assertThat(result).isNotNull();
		assertEquals(10, result.size());
	}

	private String loadJsonFromFile(String path) throws Exception {
		return new String(Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource(path).toURI())));
	}
}
