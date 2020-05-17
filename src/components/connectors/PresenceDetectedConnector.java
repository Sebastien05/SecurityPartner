package components.connectors;

import components.interfaces.PresenceDetectorCI;
import fr.sorbonne_u.components.connectors.AbstractConnector;
import interfaces.event.EventI;

public class PresenceDetectedConnector extends AbstractConnector
implements PresenceDetectorCI
{

	@Override
	public void sendEvent(String emitterURI, String destinationURI, EventI e) {
		((PresenceDetectorCI)this.offering).sendEvent(emitterURI, destinationURI,e);
		
	}

	@Override
	public void multisendEvent(String emitterURI, String[] destinationURIs, EventI e) {
		((PresenceDetectorCI)this.offering).multisendEvent(emitterURI,destinationURIs, e);		
	}

	@Override
	public void receiveEvent(String emitterURI, EventI e) {
		((PresenceDetectorCI)this.offering).receiveEvent(emitterURI, e);
	}

}