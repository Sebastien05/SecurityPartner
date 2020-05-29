package interfaces.event;

import java.io.Serializable;

/**
 *The class <code>AtomicEventI</code> extends <code>EventI</code>
 *
 * <p><strong>Description</strong></p>
 *	<p>
 * Displaying methods for adding and remove properties from the event
 * </p>
 * 
 * @author Hadrien Cazes, Sebastien Lefevre, Kady Soumahoro
 *
 */

public interface AtomicEventI extends EventI{
	
	/**
	 * Add a property in an event
	 * 
	 * @param name name of property
	 * @param value value of property
	 * @return
	 */
	Serializable putproperty(String name, Serializable value);
	
	/**
	 * Delete a property in a Event
	 * @param name name of propertie
	 * @return
	 */
	Serializable removeProperty(String name);
}
