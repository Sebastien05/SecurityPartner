package components.physicaldevices;

import java.util.Random;

import Events.Presence;
import components.CEPBus;
import components.connectors.CEPBusEventEmissionConnector;
import components.connectors.CEPBusManagementConnector;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import interfaces.component.CEPBusManagementCI;
import interfaces.component.EventEmissionCI;
import interfaces.event.AbstractAtomicEvent;
import ports.EventEmissionOutboundPort;
import ports.RegisterOutboundPort;

@RequiredInterfaces(required={EventEmissionCI.class, CEPBusManagementCI.class})

public class WindowController extends AbstractEmitterDevices {

//	private String eeopURI;
//	private String ropURI;
//	private EventEmissionOutboundPort eeop;
//	private RegisterOutboundPort rop;
//	
//	Random random;
//	
//	private int fixedTimeExecution;
//	private int fixedTimeStartExecution;
//	private int fixedDelay;
//	private int room;
//
	public static final String OPENED_WINDOW = "opened window";
	public static final String CLOSED_WINDOW = "closed window";
	public static final String WINDOW_NAME = "Window controller";
	
	protected WindowController(
		String eventEmissionOutboundPortURI,
		String registeredOutboundPortURI,
		int fixedTimeExecution,
		int fixedTimeStartExecution,
		int fixedDelay,
		int room
		)
	throws Exception
	{
		super(eventEmissionOutboundPortURI,registeredOutboundPortURI,fixedTimeExecution,fixedTimeStartExecution,fixedDelay,room);
	}
	
//	protected void	init() throws Exception
//	{
//		// Port initialization 
//		this.eeop = new EventEmissionOutboundPort(eeopURI, this) ;
//		this.rop  = new RegisterOutboundPort(ropURI, this); 
//		// Publish them
//		this.eeop.publishPort();
//		this.rop.publishPort();
//		
//		// connection with CEPBus inbound port manager for registration
//		this.doPortConnection(this.rop.getPortURI(), CEPBus.INBOUND_PORT_MANAGEMENT_URI,
//				CEPBusManagementConnector.class.getCanonicalName());
//	}
	@Override
	public void	start() throws ComponentStartException
	{
		super.start(WINDOW_NAME);
	}
//	@Override
//	public void	start() throws ComponentStartException
//	{
//		this.logMessage("starting Presence Detector component.") ;
//		super.start();
//	}
	
	@Override
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
			String eventMessage = (i==2)?
					OPENED_WINDOW:CLOSED_WINDOW;
			presence.putproperty(AbstractAtomicEvent.TYPE_PROPERTY, eventMessage);
			presence.putproperty(AbstractAtomicEvent.ROOM_PROPERTY, this.room);
			
			// SendEvent through EventEmissionOutboundPort
			this.eeop.sendEvent(eeopURI, "", presence);
			Thread.sleep(this.fixedDelay);
		}
	}
//	
//	public void finalise() throws Exception {
//		this.doPortDisconnection(this.eeop.getPortURI());
//		this.doPortDisconnection(this.rop.getPortURI());
//		super.finalise();
//	}
//	
//	public void shutdown() throws ComponentShutdownException {
//		try {
//			this.eeop.unpublishPort();
//			this.rop.unpublishPort();
//		} catch (Exception e) {
//			throw new ComponentShutdownException(e);
//		}
//		super.shutdown();
//	}
}