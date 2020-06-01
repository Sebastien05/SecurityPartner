package components.physicaldevices;

import java.util.ArrayList;

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
		int fixedTimeExecution,
		int fixedTimeStartExecution,
		int fixedDelay,
		String room,
		ArrayList<Integer> script
		)
	throws Exception
	{
		super(eventEmissionOutboundPortURI,
				fixedTimeExecution,
				fixedTimeStartExecution,
				fixedDelay,room, script);
	}
	
	@Override
	public void	start() throws ComponentStartException
	{
		super.start(PRESENCE_NAME);
	}
	
	@Override
	public void execute() throws Exception
	{
		// connection with CEPBus Event Reception inbound port for Event Emission
		String cepBusInboundPortURI = this.rop.getEventReceptionInboundPortURI(this.eeopURI);
		this.doPortConnection(this.eeopURI, cepBusInboundPortURI,
				CEPBusEventEmissionConnector.class.getCanonicalName());
		
		// component's test script 
		Thread.sleep(fixedTimeStartExecution);
		
		String eventMessage;
		for (int i=0; i < this.fixedTimeExecution; i++ ) {
			
			// Create presence event
			AbstractAtomicEvent presence = new Presence(this.room);
			if (script.contains(i)) {
				eventMessage=PRESENCE_DETECTED;
				System.out.println("[ Presence detected ] at "+this.room);
			} else
				eventMessage=NO_PRESENCE_DETECTED;
			
			presence.putproperty(AbstractAtomicEvent.TYPE_PROPERTY, eventMessage);
			// SendEvent through EventEmissionOutboundPort
			this.eeop.sendEvent(eeopURI, "", presence);
			Thread.sleep(this.fixedDelay);
		}
	}
}