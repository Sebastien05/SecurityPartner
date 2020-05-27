package components.physicaldevices;

import java.sql.Timestamp;

import CVM.CVM;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import interfaces.executor.ExecutorCI;
import interfaces.executor.ExecutorCommandI;
import ports.AlarmeInboundPort;
import ports.PresenceDetectorInboundPort;
/**
 * un composant qui peut executer des commandes pour declencher ou arreter une
 * alarme aupres d�un gardien accompagnee d�un message d�alarme
 *
 */
@OfferedInterfaces(offered={ExecutorCI.class})
public class AlarmComponent extends AbstractComponent {
	
	protected PresenceDetectorInboundPort alarmInp;
	protected String inboundPortURI;
	protected String state;
	protected Timestamp lastSwitch;
	
	public static final String ALARM_OFF = "off";
	public static final String ALARM_ON = "on";
	public static final int DURATION_ALARM = 5000; 
	
	protected AlarmComponent(String alarmInboundPortURI, int nbThreads, int nbSchedulableThreads) throws Exception {
		super(alarmInboundPortURI, 1, 1);
		this.alarmInp = new PresenceDetectorInboundPort(alarmInboundPortURI, this);
		this.alarmInp.publishPort();
		this.tracer.setTitle("provider") ;
		this.state="off";
	}
	
	protected AlarmComponent(String reflectionInboundPortURI)
	throws Exception
	{
		super(reflectionInboundPortURI, 1, 0) ;
		this.initialise() ;
	}

	protected void	initialise() throws Exception
	{
		/*this.alarmInp = this.createPort() ;
		this.alarmInp.publishPort() ;
		this.pdop = new PresenceDetectorOutboundPort(this) ;
		this.pdop.publishPort() ;*/
	}
	
	protected AlarmeInboundPort	createPort()
			throws Exception
	{
		return new AlarmeInboundPort(
				inboundPortURI, this) ;
	}
	
	/*
	 * Component execution (check its state and notify if alarm is ON)
	 */
	public void execute() throws InterruptedException {
		int currentTime = 0;
		while (currentTime<CVM.LIFE_CYCLE_DURATION/1000) {
			if (this.state==ALARM_ON)
				System.out.println("DRING DRING !!!");
			Thread.sleep(1);
			currentTime++;
		}
	}
	
	/*
	 * Method to receive command and execute it
	 */
	public void execute(ExecutorCommandI<AlarmComponent> command) {
		command.set(this);
		command.execute();
	}

	/*
	 * Turns On the alarm if an intrusion is detected
	 */
	public void turnOn(Timestamp time) {
		this.state=ALARM_ON;
		this.lastSwitch=time;
	}
	
	/*
	 * Turns off the alarm if after DURATION_ALARM there is no problem
	 */
	public void turnOff(Timestamp time) {
		if (time.compareTo(this.lastSwitch)>DURATION_ALARM) {
			this.state=ALARM_OFF;
		}
	}
}

