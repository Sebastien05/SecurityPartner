package commands;

import java.sql.Timestamp;

import components.physicaldevices.AlarmComponent;
import interfaces.executor.ExecutorCommandI;

public class TurnOFFAlarm implements ExecutorCommandI<AlarmComponent>{

	protected AlarmComponent component;
	protected Timestamp eventTime;
	
	public TurnOFFAlarm(Timestamp time) {
		this.eventTime = time;
	}
	
	@Override
	public void set(AlarmComponent o) {
		this.component=o;
	}
	
	@Override
	public void execute() {
		this.component.turnOff(this.eventTime);
	}

}
