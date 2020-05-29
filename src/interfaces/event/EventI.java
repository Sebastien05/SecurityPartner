package interfaces.event;

import java.io.Serializable;
/**
 * The class <code>EventI</code> 
 *
 * <p><strong>Description</strong></p>
 *	<p>
 * 	Used by all event
 * </p>
 * 
 * @author Hadrien Cazes, Sebastien Lefevre, Kady Soumahoro
 *
 */
public interface EventI {
	public static final String URIEventI = "uriEventI";
	
	/**
	 * Return the URI of event
	 * @return
	 */
	public String getURI() ;
	/**
	 * return the current time, the time of creation of the event
	 * @return
	 */
	public java.sql.Timestamp getTimeStamp();
	
	/**
	 * Return true if event as a property
	 * @param name name of the property searched
	 * @return
	 */
	public boolean hasProperty(String name);
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public Serializable getPropertyValue(String name);
	
	/**
	 * Return the type of the event
	 * @return
	 */
	public String getType();
}
