package com.furkanasikdev.courier.tracking.calculation.distance;

public interface DistanceCalculationStrategy {

	double calculateDistance(double startLat, double startLng, double endLat, double endLng);

}