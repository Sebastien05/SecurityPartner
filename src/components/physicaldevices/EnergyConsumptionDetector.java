package components.physicaldevices;

import java.util.Random;

import Events.EnergyReading;
import components.connectors.CEPBusEventEmissionConnector;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import interfaces.component.CEPBusManagementCI;
import interfaces.component.EventEmissionCI;
import interfaces.event.AbstractAtomicEvent;
import interfaces.physicaldevices.AbstractEmitterDevices;

/**
 * @author SecurityPartner
 * Energy Consumption Detector produces Energy Consumption events that 
 * are sent to the CEPBus to be later on treated by a correlator
 */
@RequiredInterfaces(required={EventEmissionCI.class, CEPBusManagementCI.class})

public class EnergyConsumptionDetector extends AbstractEmitterDevices{

	public static final String ENERGY_CONSUMPTION_NAME = "Energy Consumption Detector";
	public final static int ENERGYCONSUMPTIONNORMALVALUEDAY = 50000;
	public final static int ENERGYCONSUMPTIONNORMALVALUENIGHT = 10000;
	
	protected int detectedEnergyConsumption;
	

	/**
	 * @param eventEmissionOutboundPortURI
	 * @param registeredOutboundPortURI
	 * @param fixedTimeExecution
	 * @param fixedTimeStartExecution
	 * @param fixedDelay
	 * @param room
	 * @param detectedEnergyConsumption
	 * @throws Exception
	 */
	protected EnergyConsumptionDetector(
		String eventEmissionOutboundPortURI,
		int fixedTimeExecution,
		int fixedTimeStartExecution,
		int fixedDelay,
		String room,
		int detectedEnergyConsumption
		)
	throws Exception
	{
		super(eventEmissionOutboundPortURI,
				fixedTimeExecution,
				fixedTimeStartExecution,
				fixedDelay,room);
		this.detectedEnergyConsumption = detectedEnergyConsumption;
	}
	
	@Override
	public void	start() throws ComponentStartException
	{
		super.start(ENERGY_CONSUMPTION_NAME);
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
		    
			// Create energy consumption event
			AbstractAtomicEvent energyConsumption = new EnergyReading(this.room);
			
			energyConsumption.putproperty(AbstractAtomicEvent.TYPE_PROPERTY, EnergyReading.ENERGY_READING_PROPERTY);
			energyConsumption.putproperty(EnergyReading.ENERGY_VALUE_PROPERTY, detectedEnergyConsumption);
			energyConsumption.putproperty(AbstractAtomicEvent.ROOM_PROPERTY, this.room);
			
			// SendEvent through EventEmissionOutboundPort
			this.eeop.sendEvent(eeopURI, "", energyConsumption);
			Thread.sleep(this.fixedDelay);
		}
	}
}
