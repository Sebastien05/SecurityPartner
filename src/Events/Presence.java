package Events;

import interfaces.event.AbstractAtomicEvent;

public class Presence extends AbstractAtomicEvent{
	public static final String URI_presence= "presence";
	
	private static int cpt = 0;
	private int id;
	
	public Presence () {
		super();
		id=cpt++;
	}
	
	@Override
	public String getURI() {
		return URI_presence+"_"+this.id;
	}
	
	//intrusionDetected
	public String getType() {
		return URI_presence;
	}
	
}
