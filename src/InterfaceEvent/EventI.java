package InterfaceEvent;

import java.io.Serializable;
import java.security.Timestamp;

public interface EventI {
	/** URI permet de distinguer tout evenement des autres ensemble au systeme @return URI*/
	String getURI() ;
	
	/** estampille de temps, le moment auquel l'evenement a ete cree, * @return*/
	Timestamp getTimeStamp();
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	boolean hasProperty(String name);
	Serializable getPropertyValue(String name);
}
