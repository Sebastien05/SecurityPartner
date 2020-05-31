package Events;

import interfaces.event.AbstractAtomicEvent;

public class Window extends AbstractAtomicEvent{

	private static final String URI= "window";
	private static int cpt = 0;
	private int id;
	
	public Window (String room) {
		super(URI,  room);
		id=cpt++;
	}
	
	@Override
	public String getURI() {
		return URI+"_"+this.id;
	}
}
