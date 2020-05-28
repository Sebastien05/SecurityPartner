package components.physicaldevices;


import java.util.Random;

import Events.Presence;
import components.CEPBus;
import components.connectors.CEPBusEventEmissionConnector;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import interfaces.component.EventEmissionCI;
import interfaces.event.AbstractAtomicEvent;
import ports.CEPBusManagementInboundPort;
import ports.EventEmissionOutboundPort;
import ports.RegisterOutboundPort;

@RequiredInterfaces(required={EventEmissionCI.class})

public class PresenceDetector extends AbstractComponent {

	private String eeopURI;
	private String ropURI;
	private EventEmissionOutboundPort eeop;
	private RegisterOutboundPort rop;
		
	Random random;
	
	private int fixedTimeExecution;
	private int fixedTimeStartExecution;
	private int fixedDelay;
	private int room;
	
	public static final String PRESENCE_DETECTED = "Presence detected";
	public static final String NO_PRESENCE_DETECTED = "No presence detected";
	
	protected PresenceDetector(
		String eventEmissionOutboundPortURI,
		String registeredOutboundPortURI,
		int fixedTimeExecution,
		int fixedTimeStartExecution,
		int fixedDelay,
		int room
		)
	throws Exception
	{
		super(eventEmissionOutboundPortURI, 1, 0) ;
				
		this.eeopURI=eventEmissionOutboundPortURI;
		this.ropURI=registeredOutboundPortURI;
		
		this.fixedDelay=fixedDelay;
		this.fixedTimeExecution=fixedTimeExecution;
		this.fixedTimeStartExecution=fixedTimeStartExecution;
		
		random = new Random();
		this.room = room;
		
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
		
		// connection with CEPBus inbound port manager for registration
		this.doPortConnection(this.ropURI, CEPBus.INBOUND_PORT_MANAGEMENT_URI,
				CEPBusManagementInboundPort.class.getCanonicalName());
	}
	
	public void execute() throws Exception
	{
		// connection with CEPBus inbound port Event Reception for Event Emission
		String cepBusInboundPortURI = this.rop.getEventReceptionInboundPortURI(this.eeopURI);
		this.doPortConnection(this.eeopURI, cepBusInboundPortURI,
				CEPBusEventEmissionConnector.class.getCanonicalName());
		
		// component's test script 
		Thread.sleep(fixedTimeStartExecution);
		for (int i=0; i < this.fixedTimeExecution; i++ ) {
			
			// Create presence event
			AbstractAtomicEvent presence = new Presence();
			String eventMessage = (random.nextDouble()<0.2)?
					PRESENCE_DETECTED:NO_PRESENCE_DETECTED;
			presence.putproperty(AbstractAtomicEvent.TYPE_PROPERTY, eventMessage);
			presence.putproperty(AbstractAtomicEvent.ROOM_PROPERTY, this.room);
			
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