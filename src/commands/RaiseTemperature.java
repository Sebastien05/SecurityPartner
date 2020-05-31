package commands;

import components.physicaldevices.ThermostatComponent;
import fr.sorbonne_u.components.AbstractComponent;
import interfaces.component.ExecutorCommandI;


/**
 * @author SecurityPartner
 * Command that allows a ThermostatComponent to raise its temperature
 * by a set amount.
 */
public class RaiseTemperature implements ExecutorCommandI {

	protected ThermostatComponent component;
	protected int temperatureToAdd;
	
	/**
	 * Constructor for raising temperature
	 * @param temperatureToAdd integer that specifies the amount of degree that the command will be able to raise 
	 */
	public RaiseTemperature(int temperatureToAdd) {
		this.temperatureToAdd = temperatureToAdd;
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
	 * calls the method for raising Temperature in its associated component.
	 */
	@Override
	public void execute() {
		this.component.raiseTemperature(this.temperatureToAdd);
	}

}
