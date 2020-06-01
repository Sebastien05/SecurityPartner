package interfaces.rule;

import java.util.ArrayList;

import interfaces.event.EventI;

public interface RuleMultiRoomI extends RuleI {
	/**
	 * retourne l�evenement le plus recent dans la base que cet apparieur apparie
	 * @param em
	 * @return
	 */
	public EventI match(EventMatcherRoomI em, String room);
	
	/**
	 * tente un appariement sur la base d�evenements et retourne la liste des evenements
	 * apparies dans leur ordre d�occurrence ou null si aucun appariement n�a ete trouve ; c�est
	 * la methode qui exprime le patron d�evenements que la regle cherche a detecter
	 * @return
	 * @throws Exception 
	 */
	public ArrayList<EventI> trigger(String room) throws Exception;
	
}
