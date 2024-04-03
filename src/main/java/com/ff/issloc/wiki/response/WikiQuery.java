package com.ff.issloc.wiki.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WikiQuery {
    @JsonProperty("geosearch")
    private List<WikiGeoSearch> geoSearchList;

}
