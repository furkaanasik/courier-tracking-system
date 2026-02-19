package com.furkanasikdev.courier.tracking.shared.event.store.listener;

import com.furkanasikdev.courier.tracking.infrastructure.loader.StoreLoaderService;
import com.furkanasikdev.courier.tracking.shared.event.store.StoreLoadEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class StoreLoadEventListener {

	private final StoreLoaderService storeLoaderService;

	@EventListener
	public void onStoreLoadEvent(StoreLoadEvent event) {
		log.info("StoreLoadEvent received - loading stores to Redis");
		try {
			this.storeLoaderService.loadStores();
		} catch (IOException e) {
			log.error("Failed to load stores to Redis: {}", e.getMessage(), e);
		}
	}
}
