package com.furkanasikdev.courier.tracking.domain.courier;

import jakarta.persistence.Embeddable;

@Embeddable
public record Distance(double km) {

	public static Distance of(double km) {
		return new Distance(km);
	}
}
