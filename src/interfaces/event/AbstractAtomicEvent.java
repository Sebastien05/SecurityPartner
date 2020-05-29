package interfaces.event;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import interfaces.event.AtomicEventI;

/**
 * The class <code>AbstractAtomicEvent</code> implements <code>AtomicEventI</code>
 *
 * <p><strong>Description</strong></p>
 *	<p>
 * 	Used by all atomic event
 * </p>
 * 
 * @author Hadrien Cazes, Sebastien Lefevre, Kady Soumahoro
 *
 *
 */
public abstract class AbstractAtomicEvent implements AtomicEventI {

	public static final String NAME = "nameAtomicEvent";
	public static final String TYPE_PROPERTY = "type";
	public static final String ROOM_PROPERTY = "room";
	
	protected String message;
	protected Map<String, Serializable> map;
	private Timestamp eventTime;
	
	public AbstractAtomicEvent() {
		map = new HashMap<String, Serializable>();
		this.eventTime = new Timestamp((new Date()).getTime());
	}
	/**
	 * Allows distinguishing any event from the others in the whole system
	 * @return NAME String
	 */
	public String getName() {
		return NAME;
	}
	/*public abstract Serializable putproperty(String name, Serializable value);	
	public abstract Serializable removeProperty(String name);
	**/
	
	/**
	 * Add a property in an event
	 * @param name
	 * @param value  a serializable value
	 */
	@Override
	public Serializable putproperty(String name, Serializable value) {
		return this.map.put(name, value);
	}
	
	/**
	 * Remove a property in an event
	 * @param name
	 */
	@Override
	public Serializable removeProperty(String name) {
		return this.map.remove(name);
	}
	
	/**
	 * Gives the moment when the event was created
	 * 
	 */
	@Override
	public Timestamp getTimeStamp() {
		return this.eventTime;
	}
	
	/**
	 * Verify a certain property
	 * 
	 * @param name
	 */
	@Override
	public boolean hasProperty(String name) {
		return this.map.containsKey(name);
	}
	
	/**
	 * recover a property
	 * @param name
	 */
	@Override
	public Serializable getPropertyValue(String name) {
		return this.map.get(name);
	}
	
	/**
	 * Display properties
	 */
	public void displayProperties() {
		for(Entry<String,Serializable> m : map.entrySet()) {
			System.out.println("name: "+ m.getKey() +" value : "+m.getValue());
		}
	}
}
