package com.ff.issloc.wiki.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WikiGeoSearch {
	 private String title;
	    private double lat;
	    private double lon;
	    private int pageid;
	    private int ns;
	    private double dist;
	    private String primary;
	    private String country;
}
