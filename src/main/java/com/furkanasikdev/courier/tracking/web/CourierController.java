package com.furkanasikdev.courier.tracking.web;

import com.furkanasikdev.courier.tracking.application.CourierApplicationService;
import com.furkanasikdev.courier.tracking.application.StoreApplicationService;
import com.furkanasikdev.courier.tracking.application.TrackingApplicationService;
import com.furkanasikdev.courier.tracking.domain.courier.CourierLocation;
import com.furkanasikdev.courier.tracking.domain.store.StoreEntry;
import com.furkanasikdev.courier.tracking.web.doc.CourierControllerDoc;
import com.furkanasikdev.courier.tracking.web.dto.request.BatchLocationRequest;
import com.furkanasikdev.courier.tracking.web.dto.request.LocationRequest;
import com.furkanasikdev.courier.tracking.web.dto.response.BatchLocationResponse;
import com.furkanasikdev.courier.tracking.web.dto.response.DistanceResponse;
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

	private final TrackingApplicationService trackingApplicationService;
	private final CourierApplicationService courierApplicationService;
	private final StoreApplicationService storeApplicationService;

	@Override
	@PostMapping("/location")
	public ResponseEntity<Void> sendLocation(@Valid @RequestBody LocationRequest request) {
		log.debug("Location received for courier {}", request.getCourierId());

		this.trackingApplicationService.processLocation(
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
				this.trackingApplicationService.processLocation(
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
		double totalDistance = this.courierApplicationService.getTotalDistance(courierId);

		return ResponseEntity.ok(DistanceResponse.builder()
				.courierId(courierId)
				.totalDistanceKm(totalDistance)
				.build());
	}

	@Override
	@GetMapping("/{courierId}/locations")
	public ResponseEntity<List<CourierLocation>> getCourierLocations(@PathVariable String courierId) {
		return ResponseEntity.ok(this.courierApplicationService.getLocations(courierId));
	}

	@Override
	@GetMapping("/{courierId}/store-entries")
	public ResponseEntity<List<StoreEntry>> getCourierStoreEntries(@PathVariable String courierId) {
		return ResponseEntity.ok(this.storeApplicationService.getEntriesByCourierId(courierId));
	}
}
