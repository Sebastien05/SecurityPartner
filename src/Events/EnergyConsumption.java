package Events;

import interfaces.event.AbstractAtomicEvent;

public class EnergyConsumption extends AbstractAtomicEvent {
	public static final String URI_energyC= "energyCunsumption";
	private static int cpt = 0;
	private int id;
	
	public EnergyConsumption () {
		super();
		id=cpt++;
	}
	
	/**
	 * retourne l'URI de l'evenement EnergyCunsumption soit URI_energyC_X avec X correspondant
	 *  a son numero de creation, pour le distinguer des autres close pouvant 
	 *  exiter dans la base d'evenement
	 */
	@Override
	public String getURI() {
		return URI_energyC+"_"+this.id;
	}
	
	/**
	 * retourne juste le type de l'evenement EnergyConsumption soit energyConsumption
	 */
	@Override
	public String getType() {
		return URI_energyC;
	}
}
