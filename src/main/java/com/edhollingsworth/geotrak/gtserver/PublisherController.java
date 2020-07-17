package com.edhollingsworth.geotrak.gtserver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edhollingsworth.geotrak.gtserver.api.GeoTrakResolver;
import com.edhollingsworth.geotrak.gtserver.model.GeoTrak;

import reactor.core.publisher.Flux;

/**
 * Simple REST controller allows monitoring of published messages.
 *
 * @author Ed Hollingsworth
 */
@RestController
public class PublisherController {
	private final Log log = LogFactory.getLog(PublisherController.class.getName());

	@Autowired
	private GeoTrakResolver resolver;

	/**
	 * Print the status.
	 */
	@CrossOrigin(origins = "*")
    @GetMapping(path = "/api/publisher", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<GeoTrak> getEvents() {
        return resolver.getPublisher();
    }
}
