package interfaces.event;

import java.util.ArrayList;

import interfaces.event.EventI;

/**
 * The class <code>AggregateEventI</code> extends <code>EventI</code>
 *
 * 
 * @author Hadrien Cazes, Sebastien Lefevre, Kady Soumahoro
 *
 */
public interface AggregateEventI extends EventI{
	
	/**
	 * Recover all the events 
	 * @return
	 */
	ArrayList<EventI> getCorrelatedEvents();
}
