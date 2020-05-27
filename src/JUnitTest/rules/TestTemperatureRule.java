package JUnitTest.rules;

import org.junit.Test;

import Events.TemperatureReading;
import Rules.TemperatureRule;
import components.correlators.managingelement.EventBase;

public class TestTemperatureRule {

	@Test
	public void test() {
		EventBase b = new EventBase();
		
		TemperatureRule fr = new TemperatureRule();
		
		TemperatureReading temperature = new TemperatureReading();
		
		temperature.putproperty("type", "temperature");
		temperature.putproperty("temperature", 60.0);
		temperature.putproperty("room", "401");
		
		temperature.displayProperties();

		b.getEventBase().add(temperature);

		assert b.numberOfEvents() == 1;
		fr.executeOn(b);
		assert b.numberOfEvents() == 0;
	}
}