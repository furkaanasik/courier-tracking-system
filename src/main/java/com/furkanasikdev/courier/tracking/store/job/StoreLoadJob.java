package com.furkanasikdev.courier.tracking.store.job;

import com.furkanasikdev.courier.tracking.store.event.StoreLoadEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class StoreLoadJob {

	private final ApplicationEventPublisher eventPublisher;

	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationReady() {
		log.info("Application ready - publishing StoreLoadEvent");
		this.eventPublisher.publishEvent(new StoreLoadEvent(this));
	}
}