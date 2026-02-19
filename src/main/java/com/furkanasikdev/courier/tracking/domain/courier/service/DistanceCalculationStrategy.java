package com.furkanasikdev.courier.tracking.domain.courier.service;

import com.furkanasikdev.courier.tracking.domain.courier.Location;

public interface DistanceCalculationStrategy {

	double calculateDistance(Location from, Location to);

}
