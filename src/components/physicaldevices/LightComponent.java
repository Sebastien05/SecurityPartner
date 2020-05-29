package components.physicaldevices;

import java.sql.Timestamp;
import java.util.Date;

import CVM.CVM;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import interfaces.component.ExecutorCommandI;
import interfaces.component.ReceptorCommandI;
import ports.ExecutorInboundPort;

/**
 * @author SecurityPartner
 * Class that automatically switch light on/off.
 */
public class LightComponent extends AbstractComponent implements ReceptorCommandI {

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
	protected LightComponent(String lightInboundPortURI) throws Exception {
		super(1, 0);
		this.init(lightInboundPortURI);
	}

	/**
	 * initialise the Light Component port and its state
	 * @param lightInboundPortURI String that acts as Unique identifier for LightComponent
	 * @throws Exception
	 */
	protected void init(String lightInboundPortURI) throws Exception {
		this.lightInboundPort = new ExecutorInboundPort(lightInboundPortURI, this);
		this.lightInboundPort.publishPort();
		this.state = LIGHT_OFF;
		this.lastSwitch = new Timestamp((new Date()).getTime());
	}

	
	/**
	 * Set the light component as the command's owner
	 * and call the right command associated.
	 * @param command
	 */
	public void processExecute(ExecutorCommandI command) {
		command.set(this);
		command.execute();
	}

	/**
	 * Display the current status of the light at interval times
	 * until CVM start finalise method.
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
	public void turnLightON(Timestamp time) {
		if (state!=LIGHT_ON) {
			System.out.println("TURNING LIGHT ON !!!!!");
			this.state = LIGHT_ON;
			this.lastSwitch=time;
		}
	}

	public void finalise() throws Exception {
		super.finalise();
	}

	/**
	 * Unpublish LightComponent port
	 * and shut down the Light component.
	 */
	public void shutdown() throws ComponentShutdownException {
		try {
			this.lightInboundPort.unpublishPort();
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.shutdown();
	}
}
