package components.physicaldevices;

import java.sql.Timestamp;
import java.util.Date;

import CVM.CVM;
import ports.ExecutorInboundPort;

/**
 * @author SecurityPartner
 * Class that allows a correlator to switch light on/off.
 */
public class LightComponent 
extends AbstractExecutorDevices {

	protected ExecutorInboundPort lightInboundPort;
	protected String inboundPortURI;
	protected String state;
	protected Timestamp lastSwitch;

	public static final String LIGHT_OFF = "off";
	public static final String LIGHT_ON = "on";
	public static final int DURATION_LIGHT = 5000;
	
	
	/**
	 * Constructor for the light component
	 * @param lightInboundPortURI String that acts as Unique identifier for LightComponent
	 * @throws Exception
	 */
	protected LightComponent(String lightInboundPortURI) 
	throws Exception {
		super(lightInboundPortURI, 1, 0);
		this.state = LIGHT_OFF;
		this.lastSwitch = new Timestamp((new Date()).getTime());
	}

	/**
	 * Display the status of the light when a change is called by the
	 * correlator.
	 */
	public void execute() throws InterruptedException {
		int currentTime = 0;
		while (currentTime < CVM.LIFE_CYCLE_DURATION / 1000) {
			Timestamp time = new Timestamp((new Date()).getTime());
			if (time.getTime()-this.lastSwitch.getTime()>DURATION_LIGHT) {
				if (this.state == LIGHT_ON){
					System.out.println("TURNING LIGHT OFF !!!!!!");
					this.state=LIGHT_OFF;
				}
			}
			Thread.sleep(1000);
			currentTime++;
		}
	}

	/** set method for turning light on
	 * @param time argument to set the new last switched time
	 */
	public void turnONLight(Timestamp time) {
		if (state!=LIGHT_ON) {
			System.out.println("TURNING LIGHT ON !!!!!");
			this.state = LIGHT_ON;
			this.lastSwitch=time;
		}
	}

}