package com.furkanasikdev.courier.tracking.shared.event.store.listener;

import com.furkanasikdev.courier.tracking.domain.store.StoreEntry;
import com.furkanasikdev.courier.tracking.domain.store.repository.StoreEntryRepository;
import com.furkanasikdev.courier.tracking.shared.event.store.StoreEntryEvent;
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
		StoreEntry entry = StoreEntry.create(
				event.getCourierId(),
				event.getStoreName(),
				event.getCourierLocation(),
				event.getDistanceToStore(),
				event.getEntryTime()
		);

		this.storeEntryRepository.save(entry);
		log.info("Store entry logged: courier {} at {} (distance: {}km)",
				event.getCourierId(), event.getStoreName(), event.getDistanceToStore().km());
	}
}
