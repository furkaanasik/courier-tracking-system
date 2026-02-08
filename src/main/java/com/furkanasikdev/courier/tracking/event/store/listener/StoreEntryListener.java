package com.furkanasikdev.courier.tracking.event.store.listener;

import com.furkanasikdev.courier.tracking.entity.StoreEntry;
import com.furkanasikdev.courier.tracking.event.store.StoreEntryEvent;
import com.furkanasikdev.courier.tracking.repository.StoreEntryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class StoreEntryListener {

	private final StoreEntryRepository storeEntryRepository;

	@Async
	@EventListener
	public void onStoreEntry(StoreEntryEvent event) {
		StoreEntry entry = StoreEntry.builder()
				.courierId(event.getCourierId())
				.storeName(event.getStoreName())
				.entryTime(event.getEntryTime())
				.distanceToStore(event.getDistanceToStore())
				.courierLatitude(event.getCourierLatitude())
				.courierLongitude(event.getCourierLongitude())
				.build();

		this.storeEntryRepository.save(entry);
		log.info("Store entry logged: courier {} at {} (distance: {}km)", event.getCourierId(), event.getStoreName(), event.getDistanceToStore());
	}
}