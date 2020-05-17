package components.interfaces;

import interfaces.event.EventI;

/**
 * 
 *  cette est creee car le composant presence detector est rceveur et emetteur
 *  il executer winndow close
 *
 */
public  interface PresenceDetectorCI extends  EventReceptionCI, EventEmissionCI{

	@Override
	public void sendEvent(String emitterURI, String destinationURI, EventI e) ;

	@Override
	public void multisendEvent(String emitterURI, String[] destinationURIs, EventI e) ;

	@Override
	public void receiveEvent(String emitterURI, EventI e) ;


}
