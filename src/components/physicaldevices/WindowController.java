package components.physicaldevices;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

import Events.Presence;
import Events.Window;
import components.connectors.CEPBusEventEmissionConnector;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import interfaces.component.CEPBusManagementCI;
import interfaces.component.EventEmissionCI;
import interfaces.component.ExecutorCI;
import interfaces.event.AbstractAtomicEvent;
import interfaces.physicaldevices.AbstractMultiTaskDevices;

@RequiredInterfaces(required={EventEmissionCI.class, CEPBusManagementCI.class})
@OfferedInterfaces(offered={ExecutorCI.class})

public class WindowController extends AbstractMultiTaskDevices{//AbstractEmitterDevices {

	public static final String OPENED_WINDOW = "opened window";
	public static final String CLOSED_WINDOW = "closed window";
	public static final String WINDOW_NAME = "Window controller";
	
	protected WindowController(
		String componentInboundPortURI,
		String eventEmissionOutboundPortURI,
		int fixedTimeExecution,
		int fixedTimeStartExecution,
		int fixedDelay,
		String room,
		ArrayList<Integer> script
		)
	throws Exception
	{
		super(componentInboundPortURI,
				eventEmissionOutboundPortURI,
				fixedTimeExecution,
				fixedTimeStartExecution,
				fixedDelay,room, script);
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

		String eventMessage;
		for (int i=0; i < this.fixedTimeExecution; i++ ) {
			
			// Create presence event
			AbstractAtomicEvent window = new Window(this.room);
			
			if (script.contains(i)) {
				eventMessage=OPENED_WINDOW;
				System.out.println("[ Window opened ] at "+this.room);
			} else
				eventMessage=CLOSED_WINDOW;
			
			window.putproperty(AbstractAtomicEvent.TYPE_PROPERTY, eventMessage);
			
			// SendEvent through EventEmissionOutboundPort
			this.eeop.sendEvent(eeopURI, "", window);
			Thread.sleep(this.fixedDelay);
		}
	}	
	
}