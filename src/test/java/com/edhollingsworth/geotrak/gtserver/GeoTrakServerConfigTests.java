package com.edhollingsworth.geotrak.gtserver;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.leangen.graphql.spqr.spring.autoconfigure.WebSocketContextFactory;
import io.leangen.graphql.spqr.spring.web.apollo.PerConnectionApolloHandler;
import io.leangen.graphql.spqr.spring.web.servlet.websocket.GraphQLWebSocketExecutor;

@SpringBootTest
class GeoTrakServerConfigTests {
	private final Log log = LogFactory.getLog(GeoTrakServerConfigTests.class.getName());
	
	@Autowired
	private WebSocketContextFactory wsCtxFactory;
	
	@Autowired
	private GraphQLWebSocketExecutor wsGqlExecutor;

	@Autowired
	private String kafkaTopic;
	
	@Autowired
	private PerConnectionApolloHandler wsApolloHandler;

	@Test
	void instantiate() {
		log.warn("wsCtxFactory = " + wsCtxFactory);
		log.warn("wsGqlExecutor = " + wsGqlExecutor);
		log.warn("wsApolloHandler = " + wsApolloHandler);
		
		assertNotNull(wsCtxFactory);
		assertNotNull(wsGqlExecutor);
		assertNotNull(wsApolloHandler);
	}
	
	@Test
	void validate_KafkaTopic() {
		
		assertNotNull(kafkaTopic);
		assertEquals(kafkaTopic, "geotrak");
	}
}
