package Events;

import interfaces.event.AbstractAtomicEvent;

public class TemperatureReading extends AbstractAtomicEvent {

	private static final String URI_temperatureR= "temperatureReading";
	private static int cpt = 0;
	private int id;
	
	public TemperatureReading() {
		super();
		id=cpt++;
	}
	
	@Override
	public String getURI() {
		return URI_temperatureR+"_"+this.id;
	}
	
	//intrusionDetected
	public String getType() {
		return URI_temperatureR;
	}
}
