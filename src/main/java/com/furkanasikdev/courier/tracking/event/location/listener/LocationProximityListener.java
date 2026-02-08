package com.furkanasikdev.courier.tracking.event.location.listener;

import com.furkanasikdev.courier.tracking.event.location.LocationReceivedEvent;
import com.furkanasikdev.courier.tracking.event.store.StoreEntryEvent;
import com.furkanasikdev.courier.tracking.service.StoreProximityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class LocationProximityListener {

	private final StoreProximityService storeProximityService;
	private final ApplicationEventPublisher eventPublisher;

	@Async
	@EventListener
	public void onLocationReceived(LocationReceivedEvent event) {
		this.storeProximityService.findNearbyStores(event.getLatitude(), event.getLongitude())
				.forEach(result -> {
					String storeName = result.getContent().getName();

					if (!this.storeProximityService.isReentry(event.getCourierId(), storeName)) {
						this.storeProximityService.markEntry(event.getCourierId(), storeName);

						double distance = result.getDistance().getValue();

						this.eventPublisher.publishEvent(new StoreEntryEvent(
								this,
								event.getCourierId(),
								storeName,
								event.getLatitude(),
								event.getLongitude(),
								distance,
								event.getEntryTime()
						));

						log.info("Courier {} entered store {} (distance: {}km)", event.getCourierId(), storeName, distance);
					} else {
						log.debug("Reentry skipped for courier {} at store {}", event.getCourierId(), storeName);
					}
				});
	}
}