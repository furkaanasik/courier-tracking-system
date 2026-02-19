package com.furkanasikdev.courier.tracking.shared.event.location.listener;

import com.furkanasikdev.courier.tracking.application.CourierApplicationService;
import com.furkanasikdev.courier.tracking.domain.courier.service.DistanceCalculationStrategy;
import com.furkanasikdev.courier.tracking.domain.courier.Location;
import com.furkanasikdev.courier.tracking.shared.event.location.LocationReceivedEvent;
import com.furkanasikdev.courier.tracking.shared.util.ObjectMapperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class LocationDistanceListener {

	private static final String LAST_LOCATION_KEY = "courier:%s:last_location";

	private final StringRedisTemplate redisTemplate;
	private final DistanceCalculationStrategy distanceCalculator;
	private final CourierApplicationService courierApplicationService;
	private final ObjectMapperService objectMapperService;

	@Async
	@EventListener
	public void onLocationReceived(LocationReceivedEvent event) {
		String key = String.format(LAST_LOCATION_KEY, event.getCourierId());
		String lastLocationJson = this.redisTemplate.opsForValue().get(key);

		if (lastLocationJson != null) {
			try {
				Location last = this.objectMapperService.fromJson(lastLocationJson, Location.class);
				double distance = this.distanceCalculator.calculateDistance(last, event.getLocation());

				if (distance > 0) {
					this.courierApplicationService.addDistance(event.getCourierId(), distance);
					log.debug("Distance added for courier {}: {}km", event.getCourierId(), distance);
				}
			} catch (Exception e) {
				log.error("Failed to calculate distance for courier {}: {}", event.getCourierId(), e.getMessage(), e);
			}
		}

		try {
			String json = this.objectMapperService.toJson(event.getLocation());
			this.redisTemplate.opsForValue().set(key, json);
			log.debug("Last location updated in Redis for courier {}", event.getCourierId());
		} catch (Exception e) {
			log.error("Failed to update last location in Redis for courier {}: {}", event.getCourierId(), e.getMessage(), e);
		}
	}
}
