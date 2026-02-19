package com.furkanasikdev.courier.tracking.web.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BatchLocationRequest {

	@NotEmpty(message = "Locations list cannot be empty")
	@Valid
	private List<LocationRequest> locations;
}
