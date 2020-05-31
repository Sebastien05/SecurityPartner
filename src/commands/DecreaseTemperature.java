package commands;

import components.physicaldevices.ThermostatComponent;
import fr.sorbonne_u.components.AbstractComponent;
import interfaces.component.ExecutorCommandI;

/**
 * @author SecurityPartner
 * Command that allows a ThermostatComponent to lower its temperature
 * by a set amount.
 */
public class DecreaseTemperature implements ExecutorCommandI {

	protected ThermostatComponent component;
	protected int temperatureToDecrease;
	
	/**
	 * Constructor for decreasing temperature
	 * @param temperatureToDecrease integer that specifies the amount of degree that the command will be able to decrease 
	 */
	public DecreaseTemperature(int temperatureToDecrease) {
		this.temperatureToDecrease = temperatureToDecrease;
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
	 * calls the method for decreasing Temperature in its associated component.
	 */
	@Override
	public void execute() {
		this.component.lowerTemperature(this.temperatureToDecrease);
	}

}
