package com.edhollingsworth.geotrak.gtserver.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.edhollingsworth.geotrak.gtserver.model.GeoEntity;

@Repository
public interface GeoEntityRepository extends CrudRepository<GeoEntity, String> {
}
