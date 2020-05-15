package Events;

import interfaces.event.AbstractAtomicEvent;

public class Light extends AbstractAtomicEvent{
	
	private static final String URI_light= "Light";
	private static int cpt = 0;
	private int id;
	
	public Light () {
		super();
		id=cpt++;
	}
	
	@Override
	public String getURI() {
		return URI_light+"_"+this.id;
	}

	@Override
	public String getType() {
		return URI_light;
	}
}