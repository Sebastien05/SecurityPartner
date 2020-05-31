package components.physicaldevices;

import java.sql.Timestamp;
import java.util.Date;

import CVM.CVM;
import Events.Presence;
import Events.TemperatureReading;
import components.connectors.CEPBusEventEmissionConnector;
import interfaces.event.AbstractAtomicEvent;
import ports.ExecutorInboundPort;

/**
 * @author SecurityPartner 
 * 	Component that send TemperatureReading events and allows the correlator to send command
 *  to regulate its temperature
 */
public class ThermostatComponent extends AbstractMultiTaskDevices {

	protected ExecutorInboundPort thermostatInboundPort;
	protected String thermostatInboundPortURI;
	protected double temperature;
	
	protected final String HIGH_TEMPERATURE = "High temperature detected";
	protected final String NORMAL_TEMPERATURE = "Normal temperature";
	protected final String LOW_TEMPERATURE = "Low Temperature detected";

	protected static int defaultSetupTemperature;
	protected static int detectedTemperature;
	
	/**
	 * @param thermostatInboundPort set URI for the component
	 * @throws Exception
	 */
	protected ThermostatComponent(String thermostatInboundPortURI,
			String eventEmissionOutboundPortURI,
			String registeredOutboundPortURI,
			int fixedTimeExecution,
			int fixedTimeStartExecution,
			int fixedDelay,
			int room,
			int defaultSetupTemperature,
			int defaultDetectedTemperature)
	throws Exception {
		super(thermostatInboundPortURI,
				eventEmissionOutboundPortURI,
				registeredOutboundPortURI,
				fixedTimeExecution,
				fixedTimeStartExecution,
				fixedDelay,
				room);
		this.defaultSetupTemperature = defaultSetupTemperature;
		this.detectedTemperature = defaultDetectedTemperature;
	}

	/**
	 * Simulate a temperature based on detectedTemperature and attempt to add 
	 * randomness in its variation. The correlator will send commands to stabilize
	 * it toward defaultSetupTemperature.
	 * @throws Exception
	 */
	@Override
	public void execute() throws Exception {
		
		// connection with CEPBus inbound port Event Reception for Event Emission
		String cepBusInboundPortURI = this.rop.getEventReceptionInboundPortURI(this.eeopURI);
		this.doPortConnection(this.eeopURI, cepBusInboundPortURI,
				CEPBusEventEmissionConnector.class.getCanonicalName());
		
		int currentTime = 0;

		// To send multiple new TemperatureReading events to the correlator.
		while (currentTime<CVM.LIFE_CYCLE_DURATION/1000) {
		    
		    // In order to add random in the script
		    // Sudden change in temperature between -4 and +4
		    if (random.nextDouble()<0.1){
		        this.detectedTemperature += random.nextInt()%8 - 4;
		    }

		    // Temperature stabilization simulation 
		    if (this.defaultSetupTemperature != this.detectedTemperature){
		        
		        int diff = (this.defaultSetupTemperature - this.detectedTemperature);
		        int delta = diff/(Math.abs(diff));
		        // detected temperature += [-1 or +1]
		        this.detectedTemperature = this.detectedTemperature + delta;

		        // send the new temperature
		        TemperatureReading temperatureReading = new TemperatureReading();
		        String eventType = (this.detectedTemperature >= 35.0) ? HIGH_TEMPERATURE : (this.detectedTemperature <= 17.0)? LOW_TEMPERATURE: NORMAL_TEMPERATURE;
		        
		        String message = (this.defaultSetupTemperature == this.detectedTemperature)? "Stabilized temperature in room":"New temperature in room ";
		        System.out.println(message+ this.room + " is: " + this.detectedTemperature);
		      
		        // need to add manually properties for now...
		        temperatureReading.putproperty(temperatureReading.TEMP_PROPERTY, this.detectedTemperature);
		        temperatureReading.putproperty(AbstractAtomicEvent.TYPE_PROPERTY, eventType);
		        temperatureReading.putproperty(AbstractAtomicEvent.ROOM_PROPERTY, this.room);
		        this.eeop.sendEvent(eeopURI, "", temperatureReading);
		    }
		    Thread.sleep(1000);
		    currentTime++;
		}
	}

	public void lowerTemperature(int degree) {
		System.out.println("Lowering temperature by " + degree);
		this.temperature -= degree;
	}

	public void raiseTemperature(int degree) {
		System.out.println("Raising temperature ..." + degree);
		this.temperature += degree;
	}
}
