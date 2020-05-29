package commands;

import java.sql.Timestamp;

import components.physicaldevices.AlarmComponent;
import fr.sorbonne_u.components.AbstractComponent;
import interfaces.component.ExecutorCommandI;

public class TurnOFFAlarm implements ExecutorCommandI {

	protected AlarmComponent component;
	protected Timestamp eventTime;
	
	public TurnOFFAlarm(Timestamp time) {
		this.eventTime = time;
	}
	
	@Override
	public void set(AbstractComponent o) {
		this.component=(AlarmComponent) o;
	}
	
	@Override
	public void execute() {
		this.component.turnOff(this.eventTime);
	}

}
