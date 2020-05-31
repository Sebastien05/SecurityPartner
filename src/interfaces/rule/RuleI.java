package interfaces.rule;

import java.util.ArrayList;

import components.correlators.managingelement.EventBase;
import interfaces.event.EventI;

/**
 * The class <code>RuleI</code> 
 *
 * <p><strong>Description</strong></p>
 *	<p>
 * 	Used by all Rules
 * </p>
 * 
 * @author Hadrien Cazes, Sebastien Lefevre, Kady Soumahoro
 *
 */
public interface RuleI {
	
	/**
	 * return the most recent event in the database find by the matcher
	 *
	 * @param em
	 * @return
	 */
	public EventI match(EventMatcherI em);
	
	/**
	 * initialize the rule, if the data need to be initialize (by default, does nothing).
	 */
	public void init();
	
	/**
	 * Try to find a match on the basis of the events and return the list of events 
	 * found by the matcher in occurrence order.
	 * Or null if no match.
	 * The method that expresses the pattern of events that the rule seeks to detect
	 * 
	 * @return
	 * @throws Exception 
	 */
	public ArrayList<EventI> trigger() throws Exception;
	
	/**
	 * trigger actions 
	 * 
	 * @param triggeringEvents events matched found
	 */
	public void actions(ArrayList<EventI> triggeringEvents) throws Exception;
	
	/**
	 * performs side effects on internal rule data or events basis
	 * 
	 * @param triggeringEvents event matched
	 */
	public void effects(ArrayList<EventI> triggeringEvents);
	
	/**
	 *  launches the four methods one after the other
	 *  
	 * @param events
	 * @return
	 * @throws Exception 
	 */
	public boolean executeOn(EventBase events) throws Exception;
}
