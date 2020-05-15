package components.interfaces;

import fr.sorbonne_u.components.interfaces.RequiredI;
import interfaces.event.EventI;

public interface EventEmissionCI  extends RequiredI{
	
	public void sendEvent(String emitterURI, 
					String destinationURI, EventI e);
	
	public void multisendEvent(String emitterURI, 
					String [] destinationURIs, EventI e);
}
