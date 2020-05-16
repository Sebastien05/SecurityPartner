package interfaces.event;

import java.util.ArrayList;

public abstract class AbstractAggregateEvent implements AggregateEventI {
	public AbstractAggregateEvent() {
		super();
	}
	
	/**
	 * liste de tous les correlarteurs contenus dans un evenement agree 
	 */
	public abstract ArrayList<EventI> getCorrelatedEvents();
}
