package commands;

import java.sql.Timestamp;

import components.physicaldevices.LightComponent;
import fr.sorbonne_u.components.AbstractComponent;
import interfaces.component.ExecutorCommandI;

/**
 * @author SecurityPartner
 * Command that allows a LightningComponent to turn its light.
 */
public class TurnONLight implements ExecutorCommandI{

	protected LightComponent component;
	protected Timestamp eventTime;
	
	/**
	 * Constructor for turning on the light of the LightningComponent
	 * @param time timestamp that indicates the moment of creation of the command
	 */
	public TurnONLight(Timestamp time) {
		this.eventTime = time;
	}
	
	/**
	 * Link the command to its component for execute.
	 * @param AbstractComponent the component that will be linked to the command
	 */
	@Override
	public void set(AbstractComponent o) {
		this.component=(LightComponent) o;
	}

	/**
	 * Method that calls the turn on light  method of LightningComponent
	 */
	@Override
	public void execute() {
		this.component.turnONLight(eventTime);
	}
}