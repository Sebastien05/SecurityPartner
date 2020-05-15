package ports;

import components.interfaces.EventEmissionCI;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import interfaces.event.EventI;

public class AlarmInboundPort  extends AbstractInboundPort implements EventEmissionCI{
	
	private static final long serialVersionUID = 1L;
	
	public AlarmInboundPort(ComponentI owner) throws Exception {
		super (EventEmissionCI.class, owner);
	}
	
	public AlarmInboundPort(String uri, ComponentI owner) throws Exception {
		super (uri, EventEmissionCI.class, owner);
	}

	@Override
	public void sendEvent(String emitterURI, String destinationURI, EventI e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void multisendEvent(String emitterURI, String[] destinationURIs, EventI e) {
		// TODO Auto-generated method stub
		
	}
	

}
