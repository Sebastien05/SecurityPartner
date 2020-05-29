package interfaces.event;

import java.util.ArrayList;

/**
 * The class <code>AbstractAggregateEvent</code> implements <code>AggregateEventI</code>
 *
 * <p><strong>Description</strong></p>
 *	<p>
 *  Used by all aggregate event
 * </p>
 * 
 * @author Hadrien Cazes, Sebastien Lefevre, Kady Soumahoro
 */
public abstract class AbstractAggregateEvent implements AggregateEventI {
	public AbstractAggregateEvent() {
		super();
	}
	
	/**
	 * 
	 * list of all the correlartors contained in an aggregate event
	 */
	public abstract ArrayList<EventI> getCorrelatedEvents();
}
