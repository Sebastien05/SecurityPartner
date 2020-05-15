package ports;

import components.interfaces.EventEmissionCI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;
import interfaces.event.EventI;

public class EventEmissionOutboundPort extends AbstractOutboundPort
implements EventEmissionCI
{
	/**
	 * Generate serialVersionUID
	 */
	private static final long serialVersionUID = 7920770104695042370L;

	public EventEmissionOutboundPort(Class<?> implementedInterface, ComponentI owner) throws Exception {
		super(implementedInterface, owner);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void sendEvent(String emitterURI, String destinationURI, EventI e) throws Exception{
		((EventEmissionCI)this.connector).sendEvent(emitterURI,destinationURI, e);
	}

	@Override
	public void multisendEvent(String emitterURI, String[] destinationURIs, EventI e) throws Exception{
		((EventEmissionCI)this.connector).multisendEvent(emitterURI,destinationURIs, e);
	}

}
