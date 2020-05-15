package components.interfaces;

import fr.sorbonne_u.components.interfaces.OfferedI;
import interfaces.event.EventI;

public interface EventReceptionCI extends OfferedI{
	public void receiveEvent(String emitterURI, EventI e);
}
