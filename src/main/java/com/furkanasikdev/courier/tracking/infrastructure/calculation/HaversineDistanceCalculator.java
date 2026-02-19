package com.furkanasikdev.courier.tracking.infrastructure.calculation;

import com.furkanasikdev.courier.tracking.domain.courier.service.DistanceCalculationStrategy;
import com.furkanasikdev.courier.tracking.domain.courier.Location;
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
	public double calculateDistance(Location from, Location to) {
		double dLat = Math.toRadians(to.lat() - from.lat());
		double dLng = Math.toRadians(to.lng() - from.lng());

		double startLat = Math.toRadians(from.lat());
		double endLat = Math.toRadians(to.lat());

		double a = this.haversine(dLat) + Math.cos(startLat) * Math.cos(endLat) * this.haversine(dLng);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return EARTH_RADIUS_KM * c;
	}

}
