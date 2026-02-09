package com.furkanasikdev.courier.tracking.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.furkanasikdev.courier.tracking.model.Store;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreLoaderService {

	private static final String STORES_GEO_KEY = "stores:geo";

	private final GeoOperations<String, String> geoOperations;
	private final ObjectMapperService objectMapperService;

	@Value("${app.tracking.stores-file}")
	private Resource storesFile;

	public void loadStores() throws IOException {
		log.debug("Loading stores from file: {}", this.storesFile.getFilename());

		List<Store> stores = this.objectMapperService.read(this.storesFile, new TypeReference<>() {
		});
		log.debug("Parsed {} stores from JSON file", stores.size());

		stores.forEach(store -> {
			this.geoOperations.add(
					STORES_GEO_KEY,
					new RedisGeoCommands.GeoLocation<>(
							store.name(),
							new Point(store.lng(), store.lat())
					)
			);
			log.debug("Store loaded to Redis GEO: {} (lat={}, lng={})", store.name(), store.lat(), store.lng());
		});

		log.info("Total {} stores loaded to Redis GEO successfully", stores.size());
	}
}