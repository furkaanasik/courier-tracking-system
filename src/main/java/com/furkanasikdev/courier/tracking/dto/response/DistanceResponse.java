package com.furkanasikdev.courier.tracking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class DistanceResponse {

	private String courierId;
	private double totalDistanceKm;
}