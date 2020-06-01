package components.physicaldevices;

import CVM.CVM;
import Events.Presence;
import Events.Window;
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
		String room
		)
	throws Exception 
	{
		super(eventEmissionOutboundPortURI,
			  fixedTimeExecution,
			  fixedTimeStartExecution,
			  fixedDelay, room);
		this.state = CLOSED_WINDOW;
		this.lastState = this.state;
	}

	@Override
	public void execute() throws Exception {
		int currentTime = 0;
		while (currentTime<CVM.LIFE_CYCLE_DURATION/1000) {
			if (lastState != state) {
				System.out.println("[Window detector at "+this.room+"] -> "+state);
				// Create presence event
				AbstractAtomicEvent window = new Window(this.room);
				String eventMessage = (currentTime==4)?
						OPENED_WINDOW:CLOSED_WINDOW;
				window.putproperty(AbstractAtomicEvent.TYPE_PROPERTY, eventMessage);
				
				// SendEvent through EventEmissionOutboundPort
				this.eeop.sendEvent(eeopURI, "", window);
				this.lastState=state;
			}
			Thread.sleep(1000);
			currentTime++;
		}
	}

}
