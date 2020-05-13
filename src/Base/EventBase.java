package Base;

import java.io.Serializable;
import java.security.Timestamp;

import InterfaceEvent.AtomicEventI;
import InterfaceEvent.EventI;

public class EventBase implements EventI{
	/**
	 * 
	 */
	protected EventBase() {
		
		AtomicEventI temperatureReading = (AtomicEventI) new EventBase() ;
		temperatureReading.putproperty("temperature", 20.5) ;
		assert ((double)temperatureReading.getPropertyValue("temperature")) == 20.5 ;
	
	}
	/**
	 * 
	 * @param period
	 */
	public void clearEvents (Double period) {
		
	}
	
	/**
	 * 
	 */
	@Override
	public String getURI() {
		return null;
	}
	
	/**
	 * 
	 */
	@Override
	public Timestamp getTimeStamp() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 
	 */
	@Override
	public boolean hasProperty(String name) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * 
	 */
	@Override
	public Serializable getPropertyValue(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
