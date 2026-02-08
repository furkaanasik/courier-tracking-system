package com.furkanasikdev.courier.tracking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class BatchLocationResponse {

	private int totalReceived;
	private int totalProcessed;
}