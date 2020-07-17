package com.edhollingsworth.geotrak.gtserver.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.edhollingsworth.geotrak.gtserver.model.GeoTrak;

import reactor.core.publisher.EmitterProcessor;

/**
 * Main configuration class for GeoTrak (TM) Server.
 * 
 * @author Ed Hollingsworth
 */
@Configuration
@ComponentScan(basePackages = "com.edhollingsworth.geotrak")
public class GeoTrakServerConfig implements InitializingBean {
	private final Log log = LogFactory.getLog(GeoTrakServerConfig.class.getName());

	// Source for producing Events
	private final EmitterProcessor<GeoTrak> emitter = EmitterProcessor.create();
	@Bean
	public EmitterProcessor<GeoTrak> emitter() {
		return this.emitter;
	}
	
	@Value(value = "${kafka.topic}")
	private String kafkaTopic;
	
	@Bean
	public String kafkaTopic() {
		return this.kafkaTopic;
	}
	
	@Override
	public void afterPropertiesSet() {
		log.warn("Initializing GeoTrak Server...");
		
		log.warn("Loaded the EmitterProcessor: " + emitter);
	}
}
