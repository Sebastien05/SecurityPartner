package components.cepbus;

import components.interfaces.EventReceptionCI;
import fr.sorbonne_u.components.connectors.AbstractConnector;
import interfaces.event.EventI;

public class CEPBusConnector extends AbstractConnector
implements EventReceptionCI
{
	@Override
	public void receiveEvent(String emitterURI, EventI e) throws Exception {
		((EventReceptionCI)this.offering).receiveEvent(emitterURI, e);
	}
}