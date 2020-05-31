package components.physicaldevices;

import java.sql.Timestamp;
import java.util.Date;

import Events.Presence;
import components.connectors.CEPBusEventEmissionConnector;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import interfaces.component.CEPBusManagementCI;
import interfaces.component.EventEmissionCI;
import interfaces.event.AbstractAtomicEvent;
import ports.ExecutorInboundPort;

@RequiredInterfaces(required={EventEmissionCI.class, CEPBusManagementCI.class})

public class WindowController extends AbstractMultiTaskDevices{//AbstractEmitterDevices {

	public static final String OPENED_WINDOW = "opened window";
	public static final String CLOSED_WINDOW = "closed window";
	public static final String WINDOW_NAME = "Window controller";
	
	protected WindowController(
		String componentInboundPortURI,
		String eventEmissionOutboundPortURI,
		String registeredOutboundPortURI,
		int fixedTimeExecution,
		int fixedTimeStartExecution,
		int fixedDelay,
		String room
		)
	throws Exception
	{
		super(componentInboundPortURI,eventEmissionOutboundPortURI,
				registeredOutboundPortURI,fixedTimeExecution,
				fixedTimeStartExecution,fixedDelay,room);
	}
	
	@Override
	public void	start() throws ComponentStartException
	{
		super.start(WINDOW_NAME);
	}
	
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
			AbstractAtomicEvent presence = new Presence(this.room);
			String eventMessage = (i==2)?
					OPENED_WINDOW:CLOSED_WINDOW;
			presence.putproperty(AbstractAtomicEvent.TYPE_PROPERTY, eventMessage);
			
			// SendEvent through EventEmissionOutboundPort
			this.eeop.sendEvent(eeopURI, "", presence);
			Thread.sleep(this.fixedDelay);
		}
	}	
	
}