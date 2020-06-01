package interfaces.rule;

import java.util.ArrayList;

import components.correlators.managingelement.EventBase;
import interfaces.event.EventI;

public interface RuleI {
	/**
	 * initialise la regle, si des donnees doivent l�etre (par defaut, ne fait rien).
	 */
	public void init();
	
	/**
	 * prend en parametre les evenements apparies (ce qui permet aussi d�acceder a leurs
	 * proprietes si necessaire) et declenche des actions (comme lancer une alarme ou emettre un
	 * evenement complexe agregeant tout ou partie des evenements apparies)
	 * @param triggeringEvents
	 */
	public void actions(ArrayList<EventI> triggeringEvents) throws Exception;
	
	/**
	 * prend en parametre les evenements apparies et execute des effets de bords sur les
	 * donnees internes a la regle ou sur la base d�evenement
	 * @param triggeringEvents
	 */
	public void effects(ArrayList<EventI> triggeringEvents);
	/**
	 *  lance les quatres methodes les unes apres les autres 
	 * @param events
	 * @return
	 * @throws Exception 
	 */
	public boolean executeOn(EventBase events) throws Exception;
}
