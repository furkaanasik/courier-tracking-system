package com.furkanasikdev.courier.tracking.calculation.distance;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Haversine formula ile iki koordinat arası mesafe hesaplama.
 *
 * @see <a href="https://www.baeldung.com/java-find-distance-between-points">Baeldung - Distance Between Points</a>
 */
@Component("haversine")
@ConditionalOnProperty(name = "app.calculation.distance-strategy", havingValue = "haversine", matchIfMissing = true)
public class HaversineDistanceCalculator implements DistanceCalculationStrategy {

	private static final double EARTH_RADIUS_KM = 6371.0;

	private double haversine(double val) {
		return Math.pow(Math.sin(val / 2), 2);
	}

	@Override
	public double calculateDistance(double startLat, double startLng, double endLat, double endLng) {
		double dLat = Math.toRadians(endLat - startLat);
		double dLng = Math.toRadians(endLng - startLng);

		startLat = Math.toRadians(startLat);
		endLat = Math.toRadians(endLat);

		double a = this.haversine(dLat) + Math.cos(startLat) * Math.cos(endLat) * this.haversine(dLng);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return EARTH_RADIUS_KM * c;
	}

}