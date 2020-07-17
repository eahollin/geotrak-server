package com.edhollingsworth.geotrak.gtserver.model;

import java.time.OffsetDateTime;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * GeoTrak event model.  Represents a geographic position, logged by
 * a specific GeoEntity, at a specific date and time.
 * 
 * @author Ed Hollingsworth
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GeoTrak {
	@GraphQLQuery(name = "id", description = "UUID of the GeoTrak event")
	private String trakId;
	
	@GraphQLQuery(name = "geoId", description = "Unique ID of the GeoEntity")
	private String geoId;
	
	@GraphQLQuery(name = "lat", description = "Latitude")
	private float latitude;
	
	@GraphQLQuery(name = "long", description = "Longitude")
	private float longitude;
	
	@GraphQLQuery(name = "alt", description = "Altitude (in meters)")
	private float altitude;
		
	@GraphQLQuery(name = "timestamp", description = "Timestamp of this GeoTrak event")
	private OffsetDateTime timestamp;
}
