package Events;

import interfaces.event.AbstractAtomicEvent;

public class Smoke extends AbstractAtomicEvent{
	
	private static final String URI= "Smoke";
	private static int cpt = 0;
	private int id;
	
	public Smoke (String room) {
		super(URI, room);
		id=cpt++;
	}
	
	@Override
	public String getURI() {
		return URI+"_"+this.id;
	}
}
