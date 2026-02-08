package com.furkanasikdev.courier.tracking.service;

import com.furkanasikdev.courier.tracking.entity.CourierLocation;
import com.furkanasikdev.courier.tracking.event.location.LocationReceivedEvent;
import com.furkanasikdev.courier.tracking.repository.CourierLocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrackingService {

	private final CourierService courierService;
	private final CourierLocationRepository courierLocationRepository;
	private final ApplicationEventPublisher eventPublisher;

	public void processLocation(String courierId, double latitude, double longitude, LocalDateTime timestamp) {
		log.debug("Processing location for courier {}: ({}, {}) at {}", courierId, latitude, longitude, timestamp);

		this.courierService.getOrCreate(courierId);

		CourierLocation location = CourierLocation.builder()
				.courierId(courierId)
				.latitude(latitude)
				.longitude(longitude)
				.timestamp(timestamp)
				.build();
		this.courierLocationRepository.save(location);
		log.debug("Location saved for courier {}", courierId);

		this.eventPublisher.publishEvent(
				new LocationReceivedEvent(this, courierId, latitude, longitude, timestamp)
		);
		log.debug("LocationReceivedEvent published for courier {}", courierId);
	}
}