package components.physicaldevices;

import Events.Presence;
import components.connectors.CEPBusEventEmissionConnector;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import interfaces.component.CEPBusManagementCI;
import interfaces.component.EventEmissionCI;
import interfaces.event.AbstractAtomicEvent;
import interfaces.physicaldevices.AbstractEmitterDevices;

@RequiredInterfaces(required={EventEmissionCI.class, CEPBusManagementCI.class})

public class PresenceDetector extends AbstractEmitterDevices {

	public static final String PRESENCE_DETECTED = "Presence detected";
	public static final String NO_PRESENCE_DETECTED = "No presence detected";
	public static final String PRESENCE_NAME = "Presence Detector";
	
	protected PresenceDetector(
		String eventEmissionOutboundPortURI,
		String registeredOutboundPortURI,
		int fixedTimeExecution,
		int fixedTimeStartExecution,
		int fixedDelay,
		String room
		)
	throws Exception
	{
		super(eventEmissionOutboundPortURI,
				registeredOutboundPortURI,
				fixedTimeExecution,
				fixedTimeStartExecution,
				fixedDelay,room);
	}
	
	@Override
	public void	start() throws ComponentStartException
	{
		super.start(PRESENCE_NAME);
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
			String eventMessage = (i==3)?
					PRESENCE_DETECTED:NO_PRESENCE_DETECTED;
			presence.putproperty(AbstractAtomicEvent.TYPE_PROPERTY, eventMessage);
			// SendEvent through EventEmissionOutboundPort
			this.eeop.sendEvent(eeopURI, "", presence);
			Thread.sleep(this.fixedDelay);
		}
	}
}