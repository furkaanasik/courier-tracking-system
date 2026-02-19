package com.furkanasikdev.courier.tracking.shared.event.store;

import com.furkanasikdev.courier.tracking.domain.courier.Distance;
import com.furkanasikdev.courier.tracking.domain.courier.Location;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public class StoreEntryEvent extends ApplicationEvent {

	private final String courierId;
	private final String storeName;
	private final Location courierLocation;
	private final Distance distanceToStore;
	private final LocalDateTime entryTime;

	public StoreEntryEvent(Object source, String courierId, String storeName,
							Location courierLocation, Distance distanceToStore, LocalDateTime entryTime) {
		super(source);
		this.courierId = courierId;
		this.storeName = storeName;
		this.courierLocation = courierLocation;
		this.distanceToStore = distanceToStore;
		this.entryTime = entryTime;
	}
}
