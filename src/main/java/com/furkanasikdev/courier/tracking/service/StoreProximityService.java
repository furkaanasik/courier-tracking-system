package com.furkanasikdev.courier.tracking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.domain.geo.GeoReference;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

/**
 * Redis GEO komutları ile mağaza yakınlık kontrolü ve re-entry yönetimi.
 *
 * @see <a href="https://medium.com/@htyesilyurt/implementing-geospatial-indexing-in-spring-boot-using-redis-geohash-bd7b2b77e4d4">
 *     Geospatial Indexing in Spring Boot Using Redis Geohash</a>
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class StoreProximityService {

	private static final String STORES_GEO_KEY = "stores:geo";
	private static final String REENTRY_KEY_PREFIX = "courier:%s:reentry:%s";

	private final GeoOperations<String, String> geoOperations;
	private final StringRedisTemplate redisTemplate;

	@Value("${app.store.proximity-radius-meters}")
	private double proximityRadiusMeters;

	@Value("${app.store.reentry-timeout-seconds}")
	private long reentryTimeoutSeconds;

	public List<GeoResult<RedisGeoCommands.GeoLocation<String>>> findNearbyStores(double lat, double lng) {
		double radiusInKm = this.proximityRadiusMeters / 1000.0;

		Circle circle = new Circle(
				new Point(lng, lat),
				new Distance(radiusInKm, Metrics.KILOMETERS)
		);

		GeoResults<RedisGeoCommands.GeoLocation<String>> results = this.geoOperations.search(
				STORES_GEO_KEY,
				GeoReference.fromCoordinate(circle.getCenter()),
				circle.getRadius(),
				RedisGeoCommands.GeoSearchCommandArgs.newGeoSearchArgs()
						.includeDistance()
						.sortAscending()
		);

		if (results == null) {
			return Collections.emptyList();
		}

		log.debug("Found {} nearby stores for coordinates ({}, {})", results.getContent().size(), lat, lng);
		return results.getContent();
	}

	public boolean isReentry(String courierId, String storeName) {
		String key = String.format(REENTRY_KEY_PREFIX, courierId, storeName);
		return Boolean.TRUE.equals(this.redisTemplate.hasKey(key));
	}

	public void markEntry(String courierId, String storeName) {
		String key = String.format(REENTRY_KEY_PREFIX, courierId, storeName);
		this.redisTemplate.opsForValue().set(key, "1", Duration.ofSeconds(this.reentryTimeoutSeconds));
		log.debug("Marked entry for courier {} at store {} with TTL {}s", courierId, storeName, this.reentryTimeoutSeconds);
	}
}