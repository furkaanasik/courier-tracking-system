package com.furkanasikdev.courier.tracking.calculation.distance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class VincentyDistanceCalculatorTest {

	private VincentyDistanceCalculator calculator;

	@BeforeEach
	void init() {
		this.calculator = new VincentyDistanceCalculator();
	}

	@Test
	void shouldCalculateDistanceBetweenTwoStores() {
		// Ataşehir MMM Migros -> Novada MMM Migros
		double distance = this.calculator.calculateDistance(
				40.9923307, 29.1244229,
				40.986106, 29.1161293
		);

		assertThat(distance).isCloseTo(1.0, within(0.5));
	}

	@Test
	void shouldReturnZeroForSamePoint() {
		double distance = this.calculator.calculateDistance(
				40.9923307, 29.1244229,
				40.9923307, 29.1244229
		);

		assertThat(distance).isEqualTo(0.0);
	}
}