package components.physicaldevices;


import components.interfaces.EventEmissionCI;

import components.interfaces.EventReceptionCI;
import components.interfaces.PresenceDetectorCI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.interfaces.RequiredI;
import interfaces.event.EventI;
import ports.PresenceDetectorInboundPort;
import ports.PresenceDetectorOutboundPort;

@RequiredInterfaces(required={EventEmissionCI.class})
@OfferedInterfaces(offered={EventReceptionCI.class})

public class PresenceDetector extends AbstractComponent implements PresenceDetectorCI {
	public static final String		INBOUND_PORT_URI = "pdURI" ;
	protected PresenceDetectorOutboundPort pdop;
	protected PresenceDetectorInboundPort pdip;

	protected PresenceDetector() throws Exception {
		super(1, 0);
		this.initialise() ;
	}
	protected PresenceDetector(String reflectionInboundPortURI) throws Exception
	{
		super(reflectionInboundPortURI, 1, 0) ;
		this.initialise() ;
	}
	
	
	protected void	initialise() throws Exception
	{
		this.pdip = this.createPort() ;
		this.pdip.publishPort() ;
		this.pdop = new PresenceDetectorOutboundPort(this) ;
		this.pdop.publishPort() ;
	}

	protected PresenceDetectorInboundPort	createPort()
			throws Exception
	{
		return new PresenceDetectorInboundPort(
				INBOUND_PORT_URI, this) ;
	}
	@Override
	public void sendEvent(String emitterURI, String destinationURI, EventI e) {
		// TODO Auto-generated method stub
		
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