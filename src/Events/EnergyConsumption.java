package Events;

import interfaces.event.AbstractAtomicEvent;

public class EnergyConsumption extends AbstractAtomicEvent {

	private static final String URI= "energyConsumption";
	public static final String ENERGY_VALUE_PROPERTY = "Energy consumption value";
	public static final String ENERGY_READING_PROPERTY = "Energy consumption reading";
	private static int cpt = 0;
	private int id;
	
	public EnergyConsumption (String room) {
		super(URI, room);
		id=cpt++;
	}
	
	/**
	 * retourne l'URI de l'evenement EnergyCunsumption soit URI_X avec X correspondant
	 *  a son numero de creation, pour le distinguer des autres AtomicEvent pouvant 
	 *  exiter dans la base d'evenement
	 */
	@Override
	public String getURI() {
		return URI+"_"+this.id;
	}
}
