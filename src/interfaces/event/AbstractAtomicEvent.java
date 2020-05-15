package interfaces.event;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import interfaces.event.AtomicEventI;

public abstract class AbstractAtomicEvent implements AtomicEventI {

	private static final String NAME = "nameAtomicEvent";
	protected String message;
	protected Map<String, Serializable> map;
	private Timestamp eventTime;
	
	public AbstractAtomicEvent() {
		map = new HashMap<String, Serializable>();
		this.eventTime = new Timestamp((new Date()).getTime());
	}
	public String getName() {
		return NAME;
	}
	/*public abstract Serializable putproperty(String name, Serializable value);	
	public abstract Serializable removeProperty(String name);
	**/

	@Override
	public Serializable putproperty(String name, Serializable value) {
		return this.map.put(name, value);
	}

	@Override
	public Serializable removeProperty(String name) {
		return this.map.remove(name);
	}
	
	@Override
	public Timestamp getTimeStamp() {
		return this.eventTime;
	}

	@Override
	public boolean hasProperty(String name) {
		return this.map.containsKey(name);
	}

	@Override
	public Serializable getPropertyValue(String name) {
		return this.map.get(name);
	}
	
	public void displayProperties() {
		for(Entry<String,Serializable> m : map.entrySet()) {
			System.out.println("name: "+ m.getKey() +" value : "+m.getValue());
		}
	}
}
