package com.edhollingsworth.geotrak.gtserver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GeoTrakServerApplication {
	private Log log = LogFactory.getLog(GeoTrakServerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(GeoTrakServerApplication.class, args);
	}
}
