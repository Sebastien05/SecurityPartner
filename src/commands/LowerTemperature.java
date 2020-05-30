package commands;

import java.sql.Timestamp;

import components.physicaldevices.ThermostatComponent;
import fr.sorbonne_u.components.AbstractComponent;
import interfaces.component.ExecutorCommandI;

/**
 * @author SecurityPartner
 * Command that allows a ThermostatComponent to lower its temperature
 * by a set amount.
 */
public class LowerTemperature implements ExecutorCommandI {

	protected ThermostatComponent component;
	protected Timestamp eventTime;
	protected double temperatureToDecrease;
	
	public LowerTemperature(Timestamp time,double temperatureToDecrease) {
		this.eventTime = time;
		this.temperatureToDecrease = temperatureToDecrease;
	}
	
	@Override
	public void set(AbstractComponent o) {
		this.component=(ThermostatComponent) o;
	}
	
	@Override
	public void execute() {
		this.component.lowerTemperature(this.eventTime);
	}

}
