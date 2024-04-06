package com.ff.issloc.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

import java.nio.file.Files;
import java.nio.file.Paths;

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
import com.ff.issloc.wiki.response.WikiGeoSearchResponse;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class WikipediaGeoSearchServiceTest {

    @Mock
    private IssLocClient client;

    @InjectMocks
    @Autowired
    private WikipediaGeoSearchServiceImpl service;

    @Value("${issloc.baseurl.wikipedia}")
    private String baseUrlWiki;
    


    @Test
    void testFetchGeoSearch() throws Exception {
    	
    	 double latitude = 3.020882;
       double longitude = -6.841650;
       int radius = 10000;
       int limit = 30;
       String gscoordParam = latitude + "|" + longitude;
       String fullUrl = baseUrlWiki + gscoordParam + "&gsradius=" + radius + "&gslimit=" + limit
				+ "&format=json";
       
        String mockResponse = new String(Files.readAllBytes(Paths.get(new ClassPathResource("wikiGeoResponseRabat.json").getURI())));
        when(client.callExternalApiGetBody(fullUrl)).thenReturn(mockResponse);

        WikiGeoSearchResponse response = service.fetchGeoSearch(latitude, longitude, radius, limit);

        assertThat(response).isNotNull();
        assertFalse(response.getQuery().getGeoSearchList().isEmpty(), "The response list should not be empty");
        assertEquals(30,response.getQuery().getGeoSearchList().size());
    }
}

