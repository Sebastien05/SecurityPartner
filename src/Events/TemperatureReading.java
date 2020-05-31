package Events;

import interfaces.event.AbstractAtomicEvent;

public class TemperatureReading extends AbstractAtomicEvent {

	private static final String URI= "temperatureReading";
	public static final String TEMP_PROPERTY = "Current temperature";
	
	private static int cpt = 0;
	private int id;
	
	public TemperatureReading(String room) {
		super(URI, room);
		id=cpt++;
	}
	
	@Override
	public String getURI() {
		return URI+"_"+this.id;
	}
}
