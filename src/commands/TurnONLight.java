package commands;

import java.sql.Timestamp;

import components.physicaldevices.LightComponent;
import fr.sorbonne_u.components.AbstractComponent;
import interfaces.component.ExecutorCommandI;

public class TurnONLight implements ExecutorCommandI{

	protected LightComponent component;
	protected Timestamp eventTime;
	
	public TurnONLight(Timestamp time) {
		this.eventTime = time;
	}
	
	@Override
	public void set(AbstractComponent o) {
		this.component=(LightComponent) o;
	}

	@Override
	public void execute() {
		this.component.turnONLight(eventTime);
	}
}