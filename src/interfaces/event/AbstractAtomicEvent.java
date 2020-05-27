package interfaces.event;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import interfaces.event.AtomicEventI;

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
	 * i permet de distinguer tout evenement des autres dans l’esnemble du système 
	 * @return NAME String
	 */
	public String getName() {
		return NAME;
	}
	/*public abstract Serializable putproperty(String name, Serializable value);	
	public abstract Serializable removeProperty(String name);
	**/
	
	/**
	 * ajouter une propriete a un evnement
	 */
	@Override
	public Serializable putproperty(String name, Serializable value) {
		return this.map.put(name, value);
	}
	
	/**
	 * supprimer une propriete a un evnement
	 */
	@Override
	public Serializable removeProperty(String name) {
		return this.map.remove(name);
	}
	
	/**
	 *  donne le moment auquel l’evenement a ete cree
	 */
	@Override
	public Timestamp getTimeStamp() {
		return this.eventTime;
	}
	
	/**
	 * verifier une certaine propriete 
	 */
	@Override
	public boolean hasProperty(String name) {
		return this.map.containsKey(name);
	}
	
	/**
	 * recuperer une propriete
	 */
	@Override
	public Serializable getPropertyValue(String name) {
		return this.map.get(name);
	}
	
	/**
	 * afficher une propriete
	 */
	public void displayProperties() {
		for(Entry<String,Serializable> m : map.entrySet()) {
			System.out.println("name: "+ m.getKey() +" value : "+m.getValue());
		}
	}
}
