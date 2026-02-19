package com.furkanasikdev.courier.tracking.web.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DistanceResponse {

	private String courierId;
	private double totalDistanceKm;
}
