package components.correlators.managingelement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import ports.CorrelatorOutboundPort;

/**
 *  Correlator referencer : this class help to get the good correlators when
 *  an ExecutorCommand is generated in a Rule for target physical devices
 */
public class PortReferencer <T> {

	private HashMap<String, HashMap<String, T>> hash;
	
	/**
	 * Initialize the HashMap
	 */
	public PortReferencer() {
		this.hash = new HashMap<>();
	}
	
	/** Add a correlatorOutboundPort
	 * @param room define an HashMap key for component in the same room
	 * @param eventType define an HashMap key for corresponding correlator
	 * @param port CorrelatorOutboundPort/ExecutorInboundPortURI to stock
	 */
	public void addPort(
		String room,
		String eventType,
		T port
		)
	{
		assert port != null;
		HashMap<String, T> tmp = new HashMap<>();
		if (this.hash.containsKey(room)) {
			tmp = this.hash.get(room);
			tmp.put(eventType, port);
		} else {
			tmp.put(eventType, port);
			this.hash.put(room, tmp);
		}
	}
	/** Get a correlatorOutboundPort
	 * @param room define an HashMap key for component in the same room
	 * @param eventType define an HashMap key for corresponding correlator
	 * @return the good CorrelatorOutboundPort/ExecutorInboundPortURI
	 */
	public T getPort(
		String room,
		String eventType
		)
	throws Exception
	{
		T res = this.hash.get(room).get(eventType);	

		if (res == null) {
			throw new Exception("Correlator Outbound Port not found !") ;
		}
		return res;
	}

	/** Get a set of correlatorOutboundPort from one room
	 * @param room define an HashMap key for component in the same room
	 * @return an HashMap of all CorrelatorOutboundPort/ExecutorInboundPortURI in the same room
	 */
	public HashMap<String, T> getRoom(
		String room
		)
	{
		if (this.hash.containsKey(room)) {
			return this.hash.get(room);
		}
		return null;
	}
	public ArrayList<String> getAllRoom(){
		Set<String> set = this.hash.keySet();
		ArrayList<String> list = new ArrayList<>();
		list.addAll(set);
		return list;
	}
	/** Removes the corresponding CorrelatorOutboundPort/ExecutorInboundPortURI
	 *  and its eventType if a room is empty, it will be removed of hash
	 * @param room define an HashMap key for component in the same room
	 * @param eventType define an HashMap key for corresponding correlator
	 * @return true if removePort had succeed else false
	 */
	public boolean removeEventType(
		String room,
		String eventType
		)
	{
		if (this.hash.containsKey(room) &&
			this.hash.get(room).containsKey(eventType)) {
			
			this.getRoom(room).remove(eventType);
			if (this.nbEventType(room)==0) {
				this.hash.remove(room);
			}
			return true;
		}
		return false;
	}
	
	/** Give number of room where CorrelatorOutboundPort/ExecutorInboundPortURI are storage
	 * @return room number 
	 */
	public int nbRoom() {
		return this.hash.size();
	}
	
	/** Give number of CorrelatorOutboundPort/ExecutorInboundPortURI in a same room
	 * @param room define an HashMap key for component in the same room
	 * @return event number from a room
	 */
	public int nbEventType(String room) {
		HashMap<String, T> currRoom = this.getRoom(room);
		return (currRoom!=null)?currRoom.size():0;
	}
	
	public HashMap<String, HashMap<String, T>> getHash(){
		return this.hash;
	}
	
	/** Return all T port from a room
	 * @return
	 */
	public ArrayList<T> getAllPort(){
		ArrayList<T> res = new ArrayList<T>();
		for (String r : this.getAllRoom()) {
			for (Entry<String, T> ref: this.getRoom(r).entrySet()) {
				res.add(ref.getValue());
			}
		}
		return res;
	}
}
