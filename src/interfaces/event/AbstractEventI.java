package interfaces.event;

import java.io.Serializable;
import java.sql.Timestamp;

public abstract class AbstractEventI implements EventI{
	public static final String NAME= "nameEvent";
	@Override
	public String getURI() {
		// TODO Auto-generated method stub
		return null;
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
