package com.furkanasikdev.courier.tracking.service;

import com.furkanasikdev.courier.tracking.entity.Courier;
import com.furkanasikdev.courier.tracking.repository.CourierRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourierService {

	private final CourierRepository courierRepository;

	public Courier getOrCreate(String courierId) {
		return this.courierRepository.findById(courierId)
				.orElseGet(() -> {
					Courier courier = Courier.builder()
							.courierId(courierId)
							.build();
					log.info("New courier created: {}", courierId);
					return this.courierRepository.save(courier);
				});
	}

	@Transactional
	public void addDistance(String courierId, double distance) {
		Courier courier = this.getOrCreate(courierId);
		courier.setTotalDistance(courier.getTotalDistance() + distance);
		this.courierRepository.save(courier);
		log.debug("Distance updated for courier {}: +{}km, total: {}km", courierId, distance, courier.getTotalDistance());
	}

	public double getTotalDistance(String courierId) {
		return this.courierRepository.findById(courierId)
				.map(Courier::getTotalDistance)
				.orElse(0.0);
	}
}