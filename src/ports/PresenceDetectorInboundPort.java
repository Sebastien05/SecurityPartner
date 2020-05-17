package ports;

import components.interfaces.CEPBusManagementCI;
import components.interfaces.EventEmissionCI;
import components.interfaces.PresenceDetectorCI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import interfaces.event.EventI;

public class PresenceDetectorInboundPort  extends AbstractInboundPort implements PresenceDetectorCI{
	
	private static final long serialVersionUID = 1L;
	
	public PresenceDetectorInboundPort(ComponentI owner) throws Exception {
		super (EventEmissionCI.class, owner);
		assert	owner instanceof EventEmissionCI ;
	}
	
	public PresenceDetectorInboundPort(String uri, ComponentI owner) throws Exception {
		super (uri, EventEmissionCI.class, owner);
	}

	@Override
	public void sendEvent(String emitterURI, String destinationURI, EventI e) {
		((PresenceDetectorCI)this.owner).sendEvent(emitterURI, destinationURI, e);
	}

	@Override
	public void multisendEvent(String emitterURI, String[] destinationURIs, EventI e) {
		((PresenceDetectorCI)this.owner).multisendEvent(emitterURI, destinationURIs, e);
	}

	@Override
	public void receiveEvent(String emitterURI, EventI e) {
		((PresenceDetectorCI)this.owner).receiveEvent(emitterURI, e);		
	}

	

}
