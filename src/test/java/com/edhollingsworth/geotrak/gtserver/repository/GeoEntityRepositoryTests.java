package com.edhollingsworth.geotrak.gtserver.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.edhollingsworth.geotrak.gtserver.model.GeoEntity;

@SpringBootTest
class GeoEntityRepositoryTests {
	private final Log log = LogFactory.getLog(GeoEntityRepositoryTests.class.getName());
	
	@Autowired
	private GeoEntityRepository repo;
	
	@Test
	void instantiate() {
		assertNotNull(repo);
	}
	
	@Test
	void saveCreate_success() {
		GeoEntity entity = repo.save(dummyEntity());
		log.warn("Came up with this on CREATE: " + entity);
		assertNotNull(entity);
	}
	
	@Test
	void saveUpdate_success() {
		GeoEntity createdEntity = repo.save(dummyEntity());
		assertNotNull(createdEntity);
		
		// Now retrieve it
		Optional<GeoEntity> origEntity = repo.findById(createdEntity.getGeoId());
		assertTrue(origEntity.isPresent());
		
		GeoEntity entity = origEntity.get();
		log.warn("Going to update this: " + entity);
		entity.setName("Zombie Mother-in-law");
		
		GeoEntity updatedEntity = repo.save(entity);
		log.warn("Came up with this on SAVE: " + updatedEntity);
		assertNotNull(updatedEntity);
	}
	
	/**
	 * Set up a test record
	 * @return A dummy GeoEntity record
	 */
	private GeoEntity dummyEntity() {
		return new GeoEntity("ABCDEFQ", "A Dummy");
	}
}
