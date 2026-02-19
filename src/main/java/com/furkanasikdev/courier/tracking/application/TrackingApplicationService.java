package com.furkanasikdev.courier.tracking.application;

import com.furkanasikdev.courier.tracking.domain.courier.CourierLocation;
import com.furkanasikdev.courier.tracking.domain.courier.Location;
import com.furkanasikdev.courier.tracking.infrastructure.persistence.JpaCourierLocationRepository;
import com.furkanasikdev.courier.tracking.shared.event.location.LocationReceivedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrackingApplicationService {

	private final CourierApplicationService courierApplicationService;
	private final JpaCourierLocationRepository courierLocationRepository;
	private final ApplicationEventPublisher eventPublisher;

	public void processLocation(String courierId, double latitude, double longitude, LocalDateTime timestamp) {
		log.debug("Processing location for courier {}: ({}, {}) at {}", courierId, latitude, longitude, timestamp);

		this.courierApplicationService.getOrCreate(courierId);

		Location location = new Location(latitude, longitude);

		CourierLocation courierLocation = CourierLocation.create(courierId, location, timestamp);
		this.courierLocationRepository.save(courierLocation);
		log.debug("Location saved for courier {}", courierId);

		this.eventPublisher.publishEvent(new LocationReceivedEvent(this, courierId, location, timestamp));
		log.debug("LocationReceivedEvent published for courier {}", courierId);
	}
}
