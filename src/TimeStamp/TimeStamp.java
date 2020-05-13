package TimeStamp;

import java.io.Serializable;
import java.security.Timestamp;
import java.time.LocalTime;

import InterfaceEvent.EventI;

public class TimeStamp implements EventI, Comparable<TimeStamp>{
	public static final String TIME_URI = "acsiURI" ;
	
	/*** Instance d'occurence @return*/
	public LocalTime getTime() {
		return null;
	}
	/** @return*/
	public String getTimestamper() {
		return "";
	}
	
	@Override
	public int compareTo(TimeStamp ts) {
		// TODO Auto-generated method stub
		
		return 0;
	}

	@Override
	public String getURI() {
		return  TIME_URI;
	}

	@Override
	public Timestamp getTimeStamp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasProperty(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Serializable getPropertyValue(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
