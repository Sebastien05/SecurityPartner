package interfaces.event;

import java.util.ArrayList;

public abstract class AbstractAggregateEvent implements AggregateEventI {
	public AbstractAggregateEvent() {
		super();
	}
	public abstract ArrayList<EventI> getCorrelatedEvents();
}
