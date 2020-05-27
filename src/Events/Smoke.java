package Events;

import interfaces.event.AbstractAtomicEvent;

public class Smoke extends AbstractAtomicEvent{
	
	public static final String URI_smoke= "Smoke";
	private static int cpt = 0;
	private int id;
	
	public Smoke () {
		super();
		id=cpt++;
	}
	
	@Override
	public String getURI() {
		return URI_smoke+"_"+this.id;
	}

	@Override
	public String getType() {
		return URI_smoke;
	}
}
