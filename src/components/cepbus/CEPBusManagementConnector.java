package components.cepbus;

import components.interfaces.EventEmissionCI;
import components.interfaces.EventReceptionCI;
import fr.sorbonne_u.components.connectors.AbstractConnector;
import interfaces.event.EventI;

public class CEPBusManagementConnector extends AbstractConnector
implements EventEmissionCI
{

	@Override
	public void sendEvent(String emitterURI, String destinationURI, EventI e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void multisendEvent(String emitterURI, String[] destinationURIs, EventI e) {
		// TODO Auto-generated method stub
		
	}

}

