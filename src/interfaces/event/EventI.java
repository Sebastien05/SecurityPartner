package interfaces.event;

import java.io.Serializable;

public interface EventI {
	
	public String getURI() ;
	public java.sql.Timestamp getTimeStamp();
	public boolean hasProperty(String name);
	public Serializable getPropertyValue(String name);
}
