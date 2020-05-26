package components.physicaldevices;


import components.interfaces.EventEmissionCI;

import components.interfaces.EventReceptionCI;
import components.interfaces.PresenceDetectorCI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.interfaces.RequiredI;
import interfaces.event.EventI;
import ports.EventEmissionOutboundPort;
import ports.PresenceDetectorInboundPort;
import ports.PresenceDetectorOutboundPort;
import ports.RegisterOutboundPort;

@RequiredInterfaces(required={EventEmissionCI.class})

public class PresenceDetector extends AbstractComponent {

	protected String pdopURI;
	protected String ropURI;
	protected EventEmissionOutboundPort pdop;
	protected RegisterOutboundPort rop;
	
	protected PresenceDetector(
		String eventEmissionOutboundPortURI,
		String registeredOutboundPortURI
		)
	throws Exception
	{
		super(eventEmissionOutboundPortURI, 1, 0) ;
		this.pdopURI=eventEmissionOutboundPortURI;
		this.ropURI=registeredOutboundPortURI;
		this.initialise() ;
	}
	
	protected void	initialise() throws Exception
	{
		this.pdop = new EventEmissionOutboundPort(pdopURI, this) ;
		this.rop  = new RegisterOutboundPort(ropURI, this); 
		this.pdop.publishPort() ;
	}
}