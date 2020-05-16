package components.interfaces;

import fr.sorbonne_u.components.interfaces.OfferedI;
import interfaces.event.EventI;

/**
 * interface offerte qui regroupe les operations permettant de recevoir des evenements
 *
 */
public interface EventReceptionCI extends OfferedI{
	public void receiveEvent(String emitterURI, EventI e) throws Exception;
}
