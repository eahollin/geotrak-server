package com.edhollingsworth.geotrak.gtserver.api;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edhollingsworth.geotrak.gtserver.model.GeoEntity;
import com.edhollingsworth.geotrak.gtserver.model.GeoTrak;
import com.edhollingsworth.geotrak.gtserver.service.GeoTrakService;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.annotations.GraphQLSubscription;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import reactor.core.publisher.Flux;

/**
 * This class defines the GraphQL API for the GeoTrak Service.  It also contains
 * the details of connecting to the Publisher of GeoTrak events being received 
 * from Kafka (using GraphQL Subscriptions).
 * 
 * @author Ed Hollingsworth
 * @see com.edhollingsworth.geotrak.gtserver.api.GeoTrakProcessor
 * @see com.edhollingsworth.geotrak.gtserver.api.GeoTrakListener
 */
@GraphQLApi
@Service
public class GeoTrakResolver implements InitializingBean {
	private final Log log = LogFactory.getLog(GeoTrakResolver.class.getName());

	@Autowired
	private GeoTrakService gtService;

	@Autowired
	private GeoTrakProcessor processor;

	// The Publisher (Flux) to which Clients connect
	private Flux<GeoTrak> publisher;

	public GeoTrakResolver() {
		initializePublisher();
	}

	// Alternate constructor allows passing GeoTrakService (for testing)
	public GeoTrakResolver(GeoTrakService gtService) {
		this.gtService = gtService;
		initializePublisher();
	}

	@GraphQLQuery(name = "getTraks")
	public Iterable<GeoTrak> getTraks() {
		return this.gtService.list();
	}

	@GraphQLQuery(name = "getTrakById")
	public GeoTrak getTrakById(@GraphQLArgument(name = "id") long id) {
		return this.gtService.getTrakById(id).get();
	}

	@GraphQLQuery(name = "getTraksByGeoId")
	public Iterable<GeoTrak> getTraksByGeoId(@GraphQLArgument(name = "geoId") String geoId) {
		return this.gtService.getTraksByGeoId(geoId);
	}

	@GraphQLQuery(name = "getEntities")
	public Iterable<GeoEntity> getEntities() {
		return this.gtService.listEntities();
	}

	@GraphQLMutation(name = "createEntity")
	public GeoEntity createEntity(@GraphQLArgument(name = "entity") GeoEntity entity) {
		GeoEntity e = this.gtService.createEntity(entity);

		return e;
	}

	@GraphQLMutation(name = "updateEntity")
	public GeoEntity updateEntity(final GeoEntity entity) {
		return this.gtService.updateEntity(entity);
	}

	@GraphQLMutation(name = "deleteEntity")
	public void deleteEntityById(@GraphQLArgument(name = "entity") GeoEntity entity) {
		this.gtService.deleteEntity(entity);
	}

	@GraphQLMutation(name = "deleteEntityById")
	public void deleteEntityById(@GraphQLArgument(name = "id") String id) {
		this.gtService.deleteEntityById(id);
	}

	@GraphQLSubscription
	public Flux<GeoTrak> trakAdded() {
		log.info("trakAdded was invoked");

		return this.publisher; // return the Publisher
	}

	@GraphQLSubscription
	public Publisher<GeoTrak> trakAddedByEntity(@GraphQLArgument(name = "geoId") String geoId) {
		log.info("trakAddedByEntity was invoked");

		return this.publisher; // return the Publisher TODO: Limit to a single GeoEntity!
	}

	// Allows viewing the logged messages
	public Flux<GeoTrak> getPublisher() {
		return this.publisher.log();
	}

	/**
	 * Initialize the Publisher, with or without a custom cache
	 * size.  This method is invoked by both variations of the
	 * constructor.
	 */
	private void initializePublisher() {
		initializePublisher(0); // send default value
	}
	private void initializePublisher(int cacheSize) {
		// Register an anonymous GeoTrakListener implementation
		Flux<GeoTrak> bridge = Flux.create(sink -> {
			processor.register(new GeoTrakListener() {

				@Override
				public void processComplete() {
					sink.complete();
				}

				@Override
				public void onData(GeoTrak trak) {
					sink.next(trak);
				}
			});
		});
		
		// Allows broadcasting to multiple subscribers
		this.publisher = bridge.publish().autoConnect().cache(cacheSize).log();
	}
	
	@Override
	public void afterPropertiesSet() {
		// Invoking this here ensures the processor has been set
		initializePublisher();
	}
}
