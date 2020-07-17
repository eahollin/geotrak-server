package com.edhollingsworth.geotrak.gtserver.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * GeoEntity model.  Each GeoTrak is associated with a
 * GeoEntity.
 * 
 * @author Ed Hollingsworth
 */
@Data
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table//(schema="geotrak")
public class GeoEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@GraphQLQuery(name = "id", description = "UUID of the GeoEntity")
	private String geoId;
	
	@GraphQLQuery(name = "name", description = "Name of the GeoEntity")
    private String name;

}
