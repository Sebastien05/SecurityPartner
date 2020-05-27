package Events;



import interfaces.event.AbstractAtomicEvent;

public class Close extends AbstractAtomicEvent{
	
	public static final String URI_close= "Close";
	private static int cpt = 0;
	private int id;
	
	public Close () {
		super();
		id=cpt++;
	}
	/**
	 * retourne l'URI de l'evenement close soit URI_close_X avec X correspondant
	 *  a son numero de creation, pour le distinguer des autres close pouvant 
	 *  exiter dans la base d'evenement
	 */
	@Override
	public String getURI() {
		return URI_close+"_"+this.id;
	}
	
	/**
	 * retourne juste le type de l'evenement close soit Close
	 */
	@Override
	public String getType() {
		return URI_close;
	}
}
