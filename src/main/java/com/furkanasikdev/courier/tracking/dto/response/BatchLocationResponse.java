package com.furkanasikdev.courier.tracking.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BatchLocationResponse {

	private int totalReceived;
	private int totalProcessed;
}