package ports;

import components.CEPBus;
import components.interfaces.CEPBusManagementCI;
import components.interfaces.EventReceptionCI;
import components.interfaces.PresenceDetectorCI;
import components.physicaldevices.PresenceDetector;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import interfaces.event.EventI;

public class PresenceDetectorOutboundPort  
extends AbstractInboundPort 
implements PresenceDetectorCI
{
	
	private static final long serialVersionUID = 1L;
	
	public PresenceDetectorOutboundPort( ComponentI owner) 
	throws Exception
	{
		super( PresenceDetectorCI.class, owner);
		assert owner instanceof PresenceDetector;
	}
	
	public	PresenceDetectorOutboundPort(String uri, ComponentI owner)
	throws Exception
	{
			super(uri, EventReceptionCI.class, owner) ;
	}

	@Override
	public void sendEvent(String emitterURI, String destinationURI, EventI e) {
		//((PresenceDetectorCI)this.connector).send(emitterURI,destinationURI,e) ;
	}

	@Override
	public void multisendEvent(String emitterURI, String[] destinationURIs, EventI e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receiveEvent(String emitterURI, EventI e) {
		// TODO Auto-generated method stub
		
	}

	
	


}
