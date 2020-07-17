package com.edhollingsworth.geotrak.gtserver.service;

import java.util.List;
import java.util.Optional;

import com.edhollingsworth.geotrak.gtserver.model.GeoEntity;
import com.edhollingsworth.geotrak.gtserver.model.GeoTrak;

/**
 * Service interface for managing GeoEntity objects, and for reading GeoTraks.
 * 
 * @author Ed Hollingsworth
 */
public interface GeoTrakService {
	// methods for reading the event stream
	Iterable<GeoTrak> list();
	Optional<GeoTrak> getTrakById(final Long id);
	List<GeoTrak> getTraksByGeoId(final String geoId);
	
	// methods for managing GeoEntities
	Iterable<GeoEntity> listEntities();
	Optional<GeoEntity> getEntityById(final String id);
	GeoEntity createEntity(final GeoEntity entity);
	GeoEntity updateEntity(final GeoEntity entity);
	void deleteEntity(final GeoEntity entity);
	void deleteEntityById(final String id);
}
