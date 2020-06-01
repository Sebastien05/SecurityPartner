package components.physicaldevices;

import java.util.Random;

import Events.EnergyConsumption;
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

	public static final String ANOMALY = "Anormal energy consumption detected";
	public static final String NORMAL = "Energy consumption is normal";
	public static final String ENERGY_CONSUMPTION_NAME = "Energy Consumption Detector";
	
	protected Random random;
	protected static int EnergyConsumptionNormalValue;
	protected static int detectedEnergyConsumption;
	
	/**
	 * @param thermostatInboundPort set URI for the component
	 * @throws Exception
	 */
	
	
	protected EnergyConsumptionDetector(
		String eventEmissionOutboundPortURI,
		String registeredOutboundPortURI,
		int fixedTimeExecution,
		int fixedTimeStartExecution,
		int fixedDelay,
		String room,
		int EnergyConsumptionNormalValue,
		int detectedEnergyConsumption
		)
	throws Exception
	{
		// we fix the room to "general" so that the entire building rings the alarm 
		super(eventEmissionOutboundPortURI,
				registeredOutboundPortURI,
				fixedTimeExecution,
				fixedTimeStartExecution,
				fixedDelay,"general");
		this.random = new Random();
		this.EnergyConsumptionNormalValue = EnergyConsumptionNormalValue;
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
			
			// In order to add random in the script
		    // Sudden change in Energy Consumption between -4 and +4
		    if (random.nextDouble()<0.1){
		        this.detectedEnergyConsumption += random.nextInt()%8 - 4;
		    }
		    
			// Create energy consumption event
			AbstractAtomicEvent energyConsumption = new EnergyConsumption(this.room);
			
			String eventMessage = (i==3)?ANOMALY:NORMAL;
			
			energyConsumption.putproperty(AbstractAtomicEvent.TYPE_PROPERTY, eventMessage);
			energyConsumption.putproperty(EnergyConsumption.ENERGY_PROPERTY, detectedEnergyConsumption);
			energyConsumption.putproperty(AbstractAtomicEvent.ROOM_PROPERTY, this.room);
			
			if (this.detectedEnergyConsumption != this.EnergyConsumptionNormalValue) {
				System.out.println(ANOMALY);
			}
			// SendEvent through EventEmissionOutboundPort
			this.eeop.sendEvent(eeopURI, "", energyConsumption);
			Thread.sleep(this.fixedDelay);
		}
	}
}
