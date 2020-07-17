package com.edhollingsworth.geotrak.gtserver.api;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.edhollingsworth.geotrak.gtserver.model.GeoTrak;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * GeoTrakProcessor handles receiving GeoTrak events from the Kafka topic,
 * converting them to GeoTrak objects and passing them on to the appropriate
 * event handler.
 * 
 * @author Ed Hollingsworth
 */
@Service
public class GeoTrakProcessor {
	private final Log log = LogFactory.getLog(GeoTrakProcessor.class.getName());

	/**
	 *  ObjectMapper is used to convert the JSON payload received from 
	 *  Kafka into a GeoTrak object
	 */
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	static {
		OBJECT_MAPPER.findAndRegisterModules();
	}

	// Instance of GeoTrakListener
	private GeoTrakListener listener;

	/**
	 * Register a GeoTrakListener, which will be notified when messages
	 * are picked up from Kafka.
	 * @param listener The listener we are registering
	 */
	public void register(GeoTrakListener listener) {
		this.listener = listener;
	}

	/**
	 * Invoked when messages are received via Kafka.
	 * @param trak The received message.
	 */
	public void onEvent(GeoTrak trak) {
		if (listener != null) {
			listener.onData(trak);
		}
	}

	/**
	 * Invoked when the channel signals that it is finished producing
	 * messages.  This will notify clients to disconnect gracefully.
	 */
	public void onComplete() {
		if (listener != null) {
			listener.processComplete();
		}
	}

	/**
	 * Waits for messages to be received via the configured Kafka topic,
	 * converts them to GeoTrak objects, and passes them to the Event
	 * handler.
	 * @param data Raw payload received from Kafka.
	 * @throws JsonMappingException if an error occurs with the Json Mapping
	 * @throws JsonProcessingException if an error occurs during Json processing
	 */
	@KafkaListener(topics = "${kafka.topic}")
	public void receive(@Payload String data) throws JsonMappingException, JsonProcessingException { 
		// Read the GeoTrak from the Kafka topic
		GeoTrak trak = OBJECT_MAPPER.readValue(data, GeoTrak.class);

		log.info("GeoTrak object received via Kafka: " + trak);

		// Trigger the event handler, which will notify the registered Listener
		onEvent(trak);
	}
}
