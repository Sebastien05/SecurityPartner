package Events;

import interfaces.event.AbstractAtomicEvent;

public class Open extends AbstractAtomicEvent {

	private static final String URI_open= "Open";
	private static int cpt = 0;
	private int id;
	
	public Open () {
		super();
		id=cpt++;
	}
	
	@Override
	public String getURI() {
		return URI_open+"_"+this.id;
	}

	@Override
	public String getType() {
		return URI_open;
	}
}
