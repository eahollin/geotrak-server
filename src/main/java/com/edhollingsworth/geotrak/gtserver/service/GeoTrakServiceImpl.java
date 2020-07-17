package com.edhollingsworth.geotrak.gtserver.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edhollingsworth.geotrak.gtserver.model.GeoEntity;
import com.edhollingsworth.geotrak.gtserver.model.GeoTrak;
import com.edhollingsworth.geotrak.gtserver.repository.GeoEntityRepository;

/**
 * Implementation of the simplified MediaMetadata service interface.
 * 
 * @author Ed Hollingsworth
 */
@Service
public class GeoTrakServiceImpl implements GeoTrakService {
	private final Log log = LogFactory.getLog(GeoTrakServiceImpl.class.getName());
	
	@Autowired
	private GeoEntityRepository repo;
	
	@Override
	public Iterable<GeoTrak> list() {
		if (this.getClass().getName().equals("GeoTrakServiceImpl"))
			throw new UnsupportedOperationException("No implementation for Trak methods yet!");
		return null;
		/*Iterable<GeoTrak> resp = gtRepo.findAll();
		for(GeoTrak t: resp) {
			log.warn("trak: id="+t.getTrakId()+", geoId="+t.getGeoId()+", lat="+t.getLatitude());
		}
		
		return resp;*/
	}
	
	@Override
	public Optional<GeoTrak> getTrakById(final Long id) {
		if (this.getClass().getName().equals("GeoTrakServiceImpl"))
			throw new UnsupportedOperationException("No implementation for Trak methods yet!");
		return null;
		//return gtRepo.findById(id);
	}
	
	@Override
	public List<GeoTrak> getTraksByGeoId(final String geoId) {
		if (this.getClass().getName().equals("GeoTrakServiceImpl"))
			throw new UnsupportedOperationException("No implementation for Trak methods yet!");
		return null;
		// Retrieve Traks for a single GeoEntity
		//return gtRepo.findAllByGeoId(geoId);
	}
	
	@Override
	public Iterable<GeoEntity> listEntities() {
		Iterable<GeoEntity> resp = repo.findAll();
		for(GeoEntity e: resp) {
			log.warn("entity: id="+e.getGeoId()+", name="+e.getName());
		}
		
		return resp;
	}
	
	@Override
	public Optional<GeoEntity> getEntityById(final String id) {
		return repo.findById(id);
	}
	
	@Override
	public GeoEntity createEntity(final GeoEntity entity) {
		return repo.save(entity);
	}
	
	@Override
	public GeoEntity updateEntity(final GeoEntity entity) {
		Optional<GeoEntity> existingEntity = repo.findById(entity.getGeoId());
		// Ensure the record already exists
		if (!existingEntity.isPresent())
			throw new EntityNotFoundException("GeoEntity object with ID " + entity.getGeoId() + " not found!");
		
		// Assuming the record exists, update the persistent fields
		GeoEntity updatedEntity = existingEntity.get();
		updatedEntity.setName(entity.getName());
		
		// Store the updated record back to the db
		return repo.save(updatedEntity);
	}
	
	@Override
	public void deleteEntity(final GeoEntity entity) {
		repo.delete(entity);;
	}
	
	@Override
	public void deleteEntityById(final String id) {
		repo.deleteById(id);
	}
}
