package com.furkanasikdev.courier.tracking.domain.courier.repository;

import com.furkanasikdev.courier.tracking.domain.courier.Courier;
import com.furkanasikdev.courier.tracking.domain.courier.CourierLocation;

import java.util.List;
import java.util.Optional;

public interface CourierRepository {

	Optional<Courier> findById(String courierId);

	Courier save(Courier courier);

	boolean existsById(String courierId);

	List<CourierLocation> findLocations(String courierId);
}
