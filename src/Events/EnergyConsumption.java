package Events;

import interfaces.event.AbstractAtomicEvent;

public class EnergyConsumption extends AbstractAtomicEvent {
	private static final String URI_energyC= "energyCunsumption";
	private static int cpt = 0;
	private int id;
	
	public EnergyConsumption () {
		super();
		id=cpt++;
	}
	
	@Override
	public String getURI() {
		return URI_energyC+"_"+this.id;
	}

	@Override
	public String getType() {
		return URI_energyC;
	}
}
