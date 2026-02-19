package com.furkanasikdev.courier.tracking.infrastructure.calculation;

import com.furkanasikdev.courier.tracking.domain.courier.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class HaversineDistanceCalculatorTest {

	private HaversineDistanceCalculator calculator;

	@BeforeEach
	void init() {
		this.calculator = new HaversineDistanceCalculator();
	}

	@Test
	void shouldCalculateDistanceBetweenTwoStores() {
		// Ataşehir MMM Migros -> Novada MMM Migros
		double distance = this.calculator.calculateDistance(
				new Location(40.9923307, 29.1244229),
				new Location(40.986106, 29.1161293)
		);

		assertThat(distance).isCloseTo(1.0, within(0.5));
	}

	@Test
	void shouldReturnZeroForSamePoint() {
		double distance = this.calculator.calculateDistance(
				new Location(40.9923307, 29.1244229),
				new Location(40.9923307, 29.1244229)
		);

		assertThat(distance).isEqualTo(0.0);
	}

}
