package com.edhollingsworth.geotrak.gtserver.api;

import com.edhollingsworth.geotrak.gtserver.model.GeoTrak;

public interface GeoTrakListener {
	void onData(GeoTrak trak);
    void processComplete();
}
