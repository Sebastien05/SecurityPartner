package commands;

import java.sql.Timestamp;

import components.physicaldevices.AlarmComponent;
import interfaces.component.ExecutorCommandI;

public class TurnONAlarm implements ExecutorCommandI<AlarmComponent>{

	protected AlarmComponent component;
	protected Timestamp eventTime;
	
	public TurnONAlarm(Timestamp time) {
		this.eventTime = time;
	}
	
	@Override
	public void set(AlarmComponent o) {
		this.component=o;
	}

	@Override
	public void execute() {
		this.component.turnOn(this.eventTime);
	}



}
