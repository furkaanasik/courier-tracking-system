package com.furkanasikdev.courier.tracking.event.location;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public class LocationReceivedEvent extends ApplicationEvent {

	private final String courierId;
	private final double latitude;
	private final double longitude;
	private final LocalDateTime entryTime;

	public LocationReceivedEvent(Object source, String courierId, double latitude, double longitude, LocalDateTime entryTime) {
		super(source);
		this.courierId = courierId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.entryTime = entryTime;
	}
}