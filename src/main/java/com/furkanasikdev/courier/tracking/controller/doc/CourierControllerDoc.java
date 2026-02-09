package com.furkanasikdev.courier.tracking.controller.doc;

import com.furkanasikdev.courier.tracking.dto.request.BatchLocationRequest;
import com.furkanasikdev.courier.tracking.dto.request.LocationRequest;
import com.furkanasikdev.courier.tracking.dto.response.BatchLocationResponse;
import com.furkanasikdev.courier.tracking.dto.response.DistanceResponse;
import com.furkanasikdev.courier.tracking.entity.CourierLocation;
import com.furkanasikdev.courier.tracking.entity.StoreEntry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Courier", description = "Courier tracking and location management")
public interface CourierControllerDoc {

	@Operation(summary = "Send courier location", description = "Process a single GPS location for a courier")
	ResponseEntity<Void> sendLocation(@Valid @RequestBody LocationRequest request);

	@Operation(summary = "Send batch locations", description = "Process multiple GPS locations at once, sorted by timestamp")
	ResponseEntity<BatchLocationResponse> sendBatchLocations(@Valid @RequestBody BatchLocationRequest request);

	@Operation(summary = "Get total distance", description = "Get total distance traveled by a courier in kilometers")
	ResponseEntity<DistanceResponse> getTotalDistance(
			@Parameter(description = "Courier ID", example = "CRR-123") @PathVariable String courierId);

	@Operation(summary = "Get courier locations", description = "Get all location history for a courier ordered by timestamp")
	ResponseEntity<List<CourierLocation>> getCourierLocations(
			@Parameter(description = "Courier ID", example = "CRR-123") @PathVariable String courierId);

	@Operation(summary = "Get courier store entries", description = "Get all store entry logs for a courier")
	ResponseEntity<List<StoreEntry>> getCourierStoreEntries(
			@Parameter(description = "Courier ID", example = "CRR-123") @PathVariable String courierId);
}