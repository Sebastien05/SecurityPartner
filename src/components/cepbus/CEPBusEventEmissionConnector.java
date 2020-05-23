package components.cepbus;

import components.interfaces.EventEmissionCI;
import components.interfaces.EventReceptionCI;
import fr.sorbonne_u.components.connectors.AbstractConnector;
import interfaces.event.EventI;

public class CEPBusEventEmissionConnector extends AbstractConnector
implements EventEmissionCI //EventReceptionCI
{

	/*@Override
	public void receiveEvent(String emitterURI, EventI e) throws Exception {
			((EventReceptionCI)this.offering).receiveEvent(emitterURI, e);
	}*/
	/*
	@Override
	public void sendEvent(String emitterURI, String destinationURI, EventI e) throws Exception {
		((EventReceptionCI)this.offering).sendEvent(emitterURI, destinationURI, e);	
	}
	@Override
	public void multisendEvent(String emitterURI, String[] destinationURIs, EventI e) throws Exception {
		((EventReceptionCI)this.offering).multisendEvent(emitterURI, destinationURIs, e);
		
	}*/
	public void sendEvent(String emitterURI, String destinationURI, EventI e) throws Exception {
		((EventReceptionCI)this.offering).receiveEvent(emitterURI, e);
	}

	@Override
	public void multisendEvent(String emitterURI, String[] destinationURIs, EventI e) throws Exception {
		((EventReceptionCI)this.offering).receiveEvent(emitterURI, e);
	}
	
}

