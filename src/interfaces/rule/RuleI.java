package interfaces.rule;

import java.util.ArrayList;

import components.correlators.managingelement.EventBase;
import interfaces.event.EventI;

public interface RuleI {
	/**
	 * retourne l�evenement le plus recent dans la base que cet apparieur apparie
	 * @param em
	 * @return
	 */
	public EventI match(EventMatcherI em);
	/**
	 * initialise la regle, si des donnees doivent l�etre (par defaut, ne fait rien).
	 */
	public void init();
	
	/**
	 * tente un appariement sur la base d�evenements et retourne la liste des evenements
	 * apparies dans leur ordre d�occurrence ou null si aucun appariement n�a ete trouve ; c�est
	 * la methode qui exprime le patron d�evenements que la regle cherche a detecter
	 * @return
	 */
	public ArrayList<EventI> trigger();
	
	/**
	 * prend en parametre les evenements apparies (ce qui permet aussi d�acceder a leurs
	 * proprietes si necessaire) et declenche des actions (comme lancer une alarme ou emettre un
	 * evenement complexe agregeant tout ou partie des evenements apparies)
	 * @param triggeringEvents
	 */
	public void actions(ArrayList<EventI> triggeringEvents);
	
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
	 */
	public boolean executeOn(EventBase events);
}
