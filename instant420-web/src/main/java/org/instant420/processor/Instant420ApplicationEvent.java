package org.instant420.processor;

import org.springframework.context.ApplicationEvent;

public class Instant420ApplicationEvent extends ApplicationEvent {

	private static final long serialVersionUID = 2422818656934848014L;
	public enum Event{START_GEO_CODING};
	private final Event event;
	public Instant420ApplicationEvent(Object source, Event event) {
		super(source);
		this.event = event;
	}
	public Event getEvent() {
		return event;
	}
	
}
