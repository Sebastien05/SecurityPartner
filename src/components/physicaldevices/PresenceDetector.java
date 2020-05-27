package components.physicaldevices;


import java.util.Random;

import Events.Presence;
import components.interfaces.EventEmissionCI;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import interfaces.event.AbstractAtomicEvent;
import ports.EventEmissionOutboundPort;
import ports.RegisterOutboundPort;

@RequiredInterfaces(required={EventEmissionCI.class})

public class PresenceDetector extends AbstractComponent {

	private String eeopURI;
	private String ropURI;
	private EventEmissionOutboundPort eeop;
	private RegisterOutboundPort rop;
	
	private String myURI;
	
	Random random;
	
	private int fixedTimeExecution;
	private int fixedTimeStartExecution;
	private int fixedDelay;
	
	public static final String PRESENCE_DETECTED = "Presence detected";
	public static final String NO_PRESENCE_DETECTED = "No presence detected";
	
	protected PresenceDetector(
		String detectorURI,
		String eventEmissionOutboundPortURI,
		String registeredOutboundPortURI,
		int fixedTimeExecution,
		int fixedTimeStartExecution,
		int fixedDelay
		)
	throws Exception
	{
		super(eventEmissionOutboundPortURI, 1, 0) ;
		
		this.myURI=detectorURI;
		
		this.eeopURI=eventEmissionOutboundPortURI;
		this.ropURI=registeredOutboundPortURI;
		
		this.fixedDelay=fixedDelay;
		this.fixedTimeExecution=fixedTimeExecution;
		this.fixedTimeStartExecution=fixedTimeStartExecution;
		
		random = new Random();
		
		this.initialise() ;
	}
	
	protected void	initialise() throws Exception
	{
		// Port initialization 
		this.eeop = new EventEmissionOutboundPort(eeopURI, this) ;
		this.rop  = new RegisterOutboundPort(ropURI, this); 
		// Publish them
		this.eeop.publishPort();
		this.rop.publishPort();

	}
	
	public void execute() throws Exception
	{
		Thread.sleep(fixedTimeStartExecution);
		for (int i=0; i < this.fixedTimeExecution; i++ ) {
			
			// Create presence event
			AbstractAtomicEvent presence = new Presence();
			String eventMessage = (random.nextDouble()<0.2)?
					PRESENCE_DETECTED:NO_PRESENCE_DETECTED;
			presence.putproperty(this.myURI, eventMessage);
			
			// SendEvent through EventEmissionOutboundPort
			this.eeop.sendEvent(eeopURI, "", presence);
			Thread.sleep(this.fixedDelay);
		}
	}
	
	public void finalise() throws Exception {
		this.doPortDisconnection(this.eeop.getPortURI());
		this.doPortDisconnection(this.rop.getPortURI());
		super.finalise();
	}
	
	public void shutdown() throws ComponentShutdownException {
		try {
			this.eeop.unpublishPort();
			this.rop.unpublishPort();
		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdown();
	}
}