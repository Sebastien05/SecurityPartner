package components.physicaldevices;

import java.sql.Timestamp;
import java.util.Date;

import CVM.CVM;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import interfaces.component.ExecutorCI;
import interfaces.component.ExecutorCommandI;
import interfaces.component.ReceptorCommandI;
import ports.ExecutorInboundPort;
/**
 * un composant qui peut executer des commandes pour declencher ou arreter une
 * alarme aupres d�un gardien accompagnee d�un message d�alarme
 *
 */
@OfferedInterfaces(offered={ExecutorCI.class})
public class AlarmComponent
extends AbstractComponent 
implements ReceptorCommandI
{
	
	protected ExecutorInboundPort alarmInp;
	protected String inboundPortURI;
	protected String state;
	protected Timestamp lastSwitch;
	
	public static final String ALARM_OFF = "off";
	public static final String ALARM_ON = "on";
	public static final int DURATION_ALARM = 5000; 
	
	protected AlarmComponent(String alarmInboundPortURI)
	throws Exception
	{
		super(1, 0) ;
		this.init(alarmInboundPortURI) ;
	}

	protected void	init(String alarmInboundPortURI) throws Exception
	{
		this.alarmInp = new ExecutorInboundPort(alarmInboundPortURI, this);
		this.alarmInp.publishPort();
		this.state = ALARM_OFF;
		this.lastSwitch = new Timestamp((new Date()).getTime());
	}
	
	/*
	 * Component execution (check its state and notify if alarm is ON)
	 */
	public void execute() throws InterruptedException {
		int currentTime = 0;
		while (currentTime<CVM.LIFE_CYCLE_DURATION/1000) {
			if (this.state==ALARM_ON)
				System.out.println("-----------------\n"+
								   "| DRING DRING !!!\n"+
								   "-----------------  ");
			Thread.sleep(1000);
			currentTime++;
		}
	}
	
	/*
	 * Method to receive command and execute it
	 */
	@Override
	public void processExecute(ExecutorCommandI command) {
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
		if (time.getTime()-this.lastSwitch.getTime()>DURATION_ALARM) {
			this.state=ALARM_OFF;
		}
	}
	
	public void finalise() throws Exception {
		super.finalise();
	}
	
	public void shutdown()
	throws ComponentShutdownException {
		try {
			this.alarmInp.unpublishPort();
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.shutdown();
	}
}

