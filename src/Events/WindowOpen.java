package Events;

import interfaces.event.AbstractAtomicEvent;

public class WindowOpen extends AbstractAtomicEvent{
	public static final String URI_windowOp= "windowOpen";
	private static int cpt = 0;
	private int id;
	
	public WindowOpen () {
		super();
		id=cpt++;
	}
	
	@Override
	public String getURI() {
		return URI_windowOp+"_"+this.id;
	}
	
	//intrusionDetected
	public String getType() {
		return URI_windowOp;
	}
}
