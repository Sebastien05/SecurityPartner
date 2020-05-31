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
	protected static int defaultDetectedTemperature;
	
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
		this.defaultDetectedTemperature = defaultDetectedTemperature;
	}

	/**
	 * Create and send a temperatureReading event to a temperature correlator that should calibrate
	 *  the room temperature when a certain threshold is reached.
	 */
	@Override
	public void execute() throws Exception {
		
		int currentTime = 0;

		while (currentTime<CVM.LIFE_CYCLE_DURATION/1000) {
		    
		    // In order to add random in the script
		    // Sudden change in temperature between -4 and +4
		    if (random.nextDouble()<0.1){
		        this.defaultDetectedTemperature += random.nextInt()%8 - 4;
		    }

		    if (this.defaultSetupTemperature != this.defaultDetectedTemperature){
		        // Temperature stabilization simulation 
		        int diff = (this.defaultSetupTemperature - this.defaultDetectedTemperature);
		        int delta = diff/(Math.abs(diff));
		        // detected temperature += [-1 or +1]
		        this.defaultDetectedTemperature = this.defaultDetectedTemperature + delta;

		        // send the new temperature
		        TemperatureReading temperatureReading = new TemperatureReading();
		        String eventType = (this.defaultDetectedTemperature >= 35.0) ? HIGH_TEMPERATURE : (this.defaultDetectedTemperature <= 17.0)? LOW_TEMPERATURE: NORMAL_TEMPERATURE;
		        
		        String message = (this.defaultSetupTemperature == this.defaultDetectedTemperature)?: "Stabilized temperature in room":"New temperature in room ";
		        System.out.println(message+ this.room + " is: " + this.defaultDetectedTemperature);
		      
		        // Coder le constructeur de AbtractAtomicEvent pour qu'il prenne directement toutes ces propriétés
		        // Au lieu de faire dans tous les emitter à chaque fois ces 3 puts
		        temperatureReading.putproperty(temperatureReading.TEMP_PROPERTY, this.defaultDetectedTemperature);
		        temperatureReading.putproperty(AbstractAtomicEvent.TYPE_PROPERTY, eventType);
		        temperatureReading.putproperty(AbstractAtomicEvent.ROOM_PROPERTY, this.room);
		        this.eeop.sendEvent(eeopURI, "", temperatureReading);
		    }
		    Thread.sleep(1000);
		    currentTime++;
		}
	}

	public void lowerTemperature(Timestamp time) {
		System.out.println("Lowering temperature ...");
		this.temperature -= 1;
		this.lastSwitch = time;
	}

	public void raiseTemperature(Timestamp time) {
		System.out.println("Raising temperature ...");
		this.temperature += 1;
		this.lastSwitch = time;
	}
}
