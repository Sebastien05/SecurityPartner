package commands;

import java.sql.Timestamp;

import components.physicaldevices.AlarmComponent;
import fr.sorbonne_u.components.AbstractComponent;
import interfaces.component.ExecutorCommandI;

public class TurnONAlarm implements ExecutorCommandI{

	protected AlarmComponent component;
	protected Timestamp eventTime;
	
	public TurnONAlarm(Timestamp time) {
		this.eventTime = time;
	}
	
	@Override
	public void set(AbstractComponent o) {
		this.component=(AlarmComponent) o;
	}

	@Override
	public void execute() {
		this.component.turnOn(this.eventTime);
	}



}
