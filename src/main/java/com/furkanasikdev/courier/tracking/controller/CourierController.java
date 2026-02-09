package com.furkanasikdev.courier.tracking.controller;

import com.furkanasikdev.courier.tracking.controller.doc.CourierControllerDoc;
import com.furkanasikdev.courier.tracking.dto.request.BatchLocationRequest;
import com.furkanasikdev.courier.tracking.dto.request.LocationRequest;
import com.furkanasikdev.courier.tracking.dto.response.BatchLocationResponse;
import com.furkanasikdev.courier.tracking.dto.response.DistanceResponse;
import com.furkanasikdev.courier.tracking.entity.CourierLocation;
import com.furkanasikdev.courier.tracking.entity.StoreEntry;
import com.furkanasikdev.courier.tracking.service.CourierService;
import com.furkanasikdev.courier.tracking.service.StoreService;
import com.furkanasikdev.courier.tracking.service.TrackingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/v1/courier")
@RequiredArgsConstructor
@Slf4j
public class CourierController implements CourierControllerDoc {

	private final TrackingService trackingService;
	private final CourierService courierService;
	private final StoreService storeService;

	@Override
	@PostMapping("/location")
	public ResponseEntity<Void> sendLocation(@Valid @RequestBody LocationRequest request) {
		log.debug("Location received for courier {}", request.getCourierId());

		this.trackingService.processLocation(
				request.getCourierId(),
				request.getLatitude(),
				request.getLongitude(),
				request.getTimestamp()
		);

		return ResponseEntity.ok().build();
	}

	@Override
	@PostMapping("/locations/batch")
	public ResponseEntity<BatchLocationResponse> sendBatchLocations(@Valid @RequestBody BatchLocationRequest request) {
		log.debug("Batch location received: {} locations", request.getLocations().size());

		List<LocationRequest> sorted = request.getLocations().stream()
				.sorted(Comparator.comparing(LocationRequest::getTimestamp))
				.toList();

		int processed = 0;
		for (LocationRequest location : sorted) {
			try {
				this.trackingService.processLocation(
						location.getCourierId(),
						location.getLatitude(),
						location.getLongitude(),
						location.getTimestamp()
				);
				processed++;
			} catch (Exception e) {
				log.error("Failed to process location for courier {}: {}", location.getCourierId(), e.getMessage(), e);
			}
		}

		log.info("Batch processing completed: {}/{}", processed, request.getLocations().size());

		return ResponseEntity.ok(BatchLocationResponse.builder()
				.totalReceived(request.getLocations().size())
				.totalProcessed(processed)
				.build());
	}

	@Override
	@GetMapping("/{courierId}/distance")
	public ResponseEntity<DistanceResponse> getTotalDistance(@PathVariable String courierId) {
		double totalDistance = this.courierService.getTotalDistance(courierId);

		return ResponseEntity.ok(DistanceResponse.builder()
				.courierId(courierId)
				.totalDistanceKm(totalDistance)
				.build());
	}

	@Override
	@GetMapping("/{courierId}/locations")
	public ResponseEntity<List<CourierLocation>> getCourierLocations(@PathVariable String courierId) {
		return ResponseEntity.ok(this.courierService.getLocations(courierId));
	}

	@Override
	@GetMapping("/{courierId}/store-entries")
	public ResponseEntity<List<StoreEntry>> getCourierStoreEntries(@PathVariable String courierId) {
		return ResponseEntity.ok(this.storeService.getEntriesByCourierId(courierId));
	}
}