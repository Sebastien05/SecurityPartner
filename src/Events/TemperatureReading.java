package Events;

import interfaces.event.AbstractAtomicEvent;

public class TemperatureReading extends AbstractAtomicEvent {

<<<<<<< Upstream, based on master
	private static final String URI= "temperatureReading";
=======
	private static final String URI_temperatureR= "temperatureReading";
	public static final String TEMP_PROPERTY = "Current temperature";
>>>>>>> cdbb701 Added TEMP_PROPERTY constant TemperatureReading for easier use later
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
