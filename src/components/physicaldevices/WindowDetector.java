package components.physicaldevices;

import java.util.ArrayList;

import CVM.CVM;
import Events.Presence;
import Events.Window;
import components.connectors.CEPBusEventEmissionConnector;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import interfaces.component.CEPBusManagementCI;
import interfaces.component.EventEmissionCI;
import interfaces.event.AbstractAtomicEvent;
import interfaces.physicaldevices.AbstractEmitterDevices;

@RequiredInterfaces(required={EventEmissionCI.class, CEPBusManagementCI.class})

public class WindowDetector extends AbstractEmitterDevices{

	private String state;
	private String lastState;
	public static final String OPENED_WINDOW = "opened window";
	public static final String CLOSED_WINDOW = "closed window";
	
	protected WindowDetector(
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
			  fixedDelay, room, script);
		this.state = CLOSED_WINDOW;
		this.lastState = this.state;
	}

	@Override
	public void execute() throws Exception {
		int currentTime = 0;
		String eventMessage;
		
		// connection with CEPBus Event Reception inbound port for Event Emission
		String cepBusInboundPortURI = this.rop.getEventReceptionInboundPortURI(this.eeopURI);
		this.doPortConnection(this.eeopURI, cepBusInboundPortURI,
				CEPBusEventEmissionConnector.class.getCanonicalName());
		
		while (currentTime<CVM.LIFE_CYCLE_DURATION/1000) {
			// Create presence event
			AbstractAtomicEvent window = new Window(this.room);
			if (script.contains(currentTime)) {
				eventMessage=OPENED_WINDOW;
				System.out.println("[ Window opened ] at "+this.room);
			} else
				eventMessage=CLOSED_WINDOW;
			
			window.putproperty(AbstractAtomicEvent.TYPE_PROPERTY, eventMessage);
			
			// SendEvent through EventEmissionOutboundPort
			this.eeop.sendEvent(eeopURI, "", window);
			this.lastState=state;
			Thread.sleep(1000);
			currentTime++;
		}
	}

}
