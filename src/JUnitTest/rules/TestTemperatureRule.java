package JUnitTest.rules;

import org.junit.Test;

import Events.TemperatureReading;
import Rules.TemperatureRule;
import components.correlators.managingelement.EventBase;
import interfaces.event.AbstractAtomicEvent;

public class TestTemperatureRule {

	@Test
	public void test() {
		EventBase b = new EventBase();
		
		TemperatureRule fr = new TemperatureRule();
		
		TemperatureReading temperature = new TemperatureReading("401");
		
		temperature.putproperty(AbstractAtomicEvent.TYPE_PROPERTY, "temperature");
		temperature.putproperty("temperature", 60.0);
		
		temperature.displayProperties();

		b.getEventBase().add(temperature);

		assert b.numberOfEvents() == 1;
		try {
			fr.executeOn(b);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assert b.numberOfEvents() == 0;
	}
}