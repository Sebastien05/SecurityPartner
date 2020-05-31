package commands;

import java.sql.Timestamp;

import components.physicaldevices.AlarmComponent;
import fr.sorbonne_u.components.AbstractComponent;
import interfaces.component.ExecutorCommandI;

/**
 * @author SecurityPartner
 * Command that allows an AlarmComponent to activate its alarm signal
 */
public class TurnONAlarm implements ExecutorCommandI{

	protected AlarmComponent component;
	protected Timestamp eventTime;
	
	/**
	 * Constructor for turning on the alarm component
	 * @param time timestamp that indicates the moment of creation of the command
	 */
	public TurnONAlarm(Timestamp time) {
		this.eventTime = time;
	}
	
	/**
	 * Link the command to its component for execute.
	 * @param AbstractComponent the component that will be linked to the command
	 */
	@Override
	public void set(AbstractComponent o) {
		this.component=(AlarmComponent) o;
	}

	/**
	 * Method that calls the turn on method of AlarmComponent
	 */
	@Override
	public void execute() {
		this.component.turnOn(this.eventTime);
	}



}
