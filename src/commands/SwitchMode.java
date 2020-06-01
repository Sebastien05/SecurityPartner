package commands;

import java.sql.Timestamp;

import components.physicaldevices.ThermostatComponent;
import fr.sorbonne_u.components.AbstractComponent;
import interfaces.component.ExecutorCommandI;

/**
 * @author SecurityPartner
 * Command that allows a ThermostatComponent to switch its mode
 */
public class SwitchMode implements ExecutorCommandI {

	protected ThermostatComponent component;
	protected String state;
	
	
	/**
	 * @param state to switch to for a ThermostatComponent
	 */
	public SwitchMode(String state) {
		this.state = state;
	}
	
	/**
	 * Link the command to its component for execute.
	 * @param AbstractComponent the component that will be linked to the command
	 */
	@Override
	public void set(AbstractComponent o) {
		this.component=(ThermostatComponent) o;
	}
	
	/**
	 * Method that calls the switchMode method of ThermostatComponent
	 */
	@Override
	public void execute() {
		this.component.switchMode(this.state);
	}

}
