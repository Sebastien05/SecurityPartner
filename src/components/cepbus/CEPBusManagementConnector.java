package components.cepbus;

import components.interfaces.EventEmissionCI;
import fr.sorbonne_u.components.connectors.AbstractConnector;
import interfaces.event.EventI;

public class CEPBusManagementConnector extends AbstractConnector
implements EventEmissionCI
{

	@Override
	public void sendEvent(String emitterURI, String destinationURI, EventI e) throws Exception {
		((EventEmissionCI)this.offering).sendEvent(emitterURI, destinationURI, e);
	}

	@Override
	public void multisendEvent(String emitterURI, String[] destinationURIs, EventI e) throws Exception {
		((EventEmissionCI)this.offering).multisendEvent(emitterURI, destinationURIs, e);
	}
}

