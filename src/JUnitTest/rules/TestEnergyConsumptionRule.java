package JUnitTest.rules;

import org.junit.Test;

import Events.EnergyConsumption;
import Rules.EnergyConsumptionRule;
import components.correlators.managingelement.EventBase;
import interfaces.event.AbstractAtomicEvent;

public class TestEnergyConsumptionRule {

	@Test
	public void test() {
		EventBase b = new EventBase();
		
		EnergyConsumptionRule ecr = new EnergyConsumptionRule();
		
		EnergyConsumption energyConsumption = new EnergyConsumption("401");
		
		energyConsumption.putproperty(AbstractAtomicEvent.TYPE_PROPERTY, "energy consumption");
		energyConsumption.putproperty("energyValue", 300.0);
		
		energyConsumption.displayProperties();

		b.getEventBase().add(energyConsumption);

		assert b.numberOfEvents() == 1;
		try {
			ecr.executeOn(b);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assert b.numberOfEvents() == 0;
	}
}