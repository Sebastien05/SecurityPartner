package interfaces.event;

import java.util.ArrayList;

import interfaces.event.EventI;

public interface AggregateEventI extends EventI{
	ArrayList<EventI> getCorrelatedEvents();
}
