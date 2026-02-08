package com.furkanasikdev.courier.tracking.event.store;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public class StoreEntryEvent extends ApplicationEvent {

	private final String courierId;
	private final String storeName;
	private final double courierLatitude;
	private final double courierLongitude;
	private final double distanceToStore;
	private final LocalDateTime timestamp;

	public StoreEntryEvent(Object source, String courierId, String storeName,
	                       double courierLatitude, double courierLongitude,
	                       double distanceToStore, LocalDateTime timestamp) {
		super(source);
		this.courierId = courierId;
		this.storeName = storeName;
		this.courierLatitude = courierLatitude;
		this.courierLongitude = courierLongitude;
		this.distanceToStore = distanceToStore;
		this.timestamp = timestamp;
	}
}