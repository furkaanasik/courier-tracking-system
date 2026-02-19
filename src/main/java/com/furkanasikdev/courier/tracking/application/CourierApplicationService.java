package com.furkanasikdev.courier.tracking.application;

import com.furkanasikdev.courier.tracking.domain.courier.Courier;
import com.furkanasikdev.courier.tracking.domain.courier.CourierLocation;
import com.furkanasikdev.courier.tracking.domain.courier.repository.CourierRepository;
import com.furkanasikdev.courier.tracking.domain.courier.Distance;
import com.furkanasikdev.courier.tracking.shared.exception.CourierNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourierApplicationService {

	private final CourierRepository courierRepository;

	public Courier getOrCreate(String courierId) {
		return this.courierRepository.findById(courierId)
				.orElseGet(() -> {
					Courier courier = Courier.create(courierId);
					log.info("New courier created: {}", courierId);
					return this.courierRepository.save(courier);
				});
	}

	@Transactional
	public void addDistance(String courierId, double distance) {
		Courier courier = this.getOrCreate(courierId);
		courier.addDistance(Distance.of(distance));
		this.courierRepository.save(courier);
		log.debug("Distance updated for courier {}: +{}km, total: {}km", courierId, distance, courier.getTotalDistance().km());
	}

	public double getTotalDistance(String courierId) {
		return this.courierRepository.findById(courierId)
				.map(c -> c.getTotalDistance().km())
				.orElseThrow(() -> new CourierNotFoundException(courierId));
	}

	public List<CourierLocation> getLocations(String courierId) {
		if (!this.courierRepository.existsById(courierId)) {
			throw new CourierNotFoundException(courierId);
		}
		return this.courierRepository.findLocations(courierId);
	}
}
