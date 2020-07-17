package com.edhollingsworth.geotrak.gtserver;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.time.Duration;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;

import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class GeoTrakServerApplicationTests {

    @Autowired
    MockMvc mockMvc;
    
	@LocalServerPort
    int randomServerPort;
	
	@Test
	void contextLoads() {
	}
	
	@Disabled
	@Test
    void testQuery() throws Exception {
		String expectedResponse = "{\"data\":{\"getMetadata\":[]}}";
        mockMvc.perform(MockMvcRequestBuilders.post("/graphql")
                .content("{\"query\":\"{ getMetadata { id } }\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse))
                .andReturn();
    }
	
	/*
	 * Opens a WebSocket connection to the server application and waits for 10 seconds,
	 * accepting any emitted events that are sent during that duration.
	 */
	@Disabled
	@Test
	void testWebSocket() {
		WebSocketClient client = new ReactorNettyWebSocketClient();
        client.execute(
          URI.create("ws://localhost:"+randomServerPort+"/ws/graphql"), 
          session -> session.send(
            Mono.just(session.textMessage("{\"subscription\":\"{ mediaAdded{id}}\"}")))
            .thenMany(session.receive()
              .map(WebSocketMessage::getPayloadAsText)
              .log())
            .then())
            .block(Duration.ofSeconds(60L));
	}

}
