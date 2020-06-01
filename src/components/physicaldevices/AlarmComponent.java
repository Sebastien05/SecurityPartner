package components.physicaldevices;

import java.sql.Timestamp;
import java.util.Date;

import CVM.CVM;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import interfaces.component.ExecutorCI;
import interfaces.physicaldevices.AbstractExecutorDevices;
/**
 * un composant qui peut executer des commandes pour declencher ou arreter une
 * alarme aupres d�un gardien accompagnee d�un message d�alarme
 *
 */
@OfferedInterfaces(offered={ExecutorCI.class})
public class AlarmComponent
extends AbstractExecutorDevices 
{
	public static final String ALARM_OFF = "off";
	public static final String ALARM_ON = "on";
	public static final int DURATION_ALARM = 5000; 
	private Timestamp lastSwitch;

	
	protected AlarmComponent(
		String alarmInboundPortURI,
		String room
		)
	throws Exception
	{
		super(alarmInboundPortURI,room);
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
}

