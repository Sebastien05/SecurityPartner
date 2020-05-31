package commands;

import java.sql.Timestamp;

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
	
	public RaiseTemperature(int temperatureToAdd) {
		this.temperatureToAdd = temperatureToAdd;
	}
	
	@Override
	public void set(AbstractComponent o) {
		this.component=(ThermostatComponent) o;
	}
	
	@Override
	public void execute() {
		this.component.raiseTemperature(this.temperatureToAdd);
	}

}
