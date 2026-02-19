package com.furkanasikdev.courier.tracking.shared.event.location;

import com.furkanasikdev.courier.tracking.domain.courier.Location;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public class LocationReceivedEvent extends ApplicationEvent {

	private final String courierId;
	private final Location location;
	private final LocalDateTime entryTime;

	public LocationReceivedEvent(Object source, String courierId, Location location, LocalDateTime entryTime) {
		super(source);
		this.courierId = courierId;
		this.location = location;
		this.entryTime = entryTime;
	}
}
