package com.furkanasikdev.courier.tracking.store.event;

import org.springframework.context.ApplicationEvent;

public class StoreLoadEvent extends ApplicationEvent {

	public StoreLoadEvent(Object source) {
		super(source);
	}

}