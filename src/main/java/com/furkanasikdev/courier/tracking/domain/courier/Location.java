package com.furkanasikdev.courier.tracking.domain.courier;

import jakarta.persistence.Embeddable;

@Embeddable
public record Location(double lat, double lng) {
}
