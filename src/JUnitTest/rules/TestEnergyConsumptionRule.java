package JUnitTest.rules;

import org.junit.Test;

import Events.EnergyConsumption;
import Rules.EnergyConsumptionRule;
import components.correlators.managingelement.EventBase;

public class TestEnergyConsumptionRule {

	@Test
	public void test() {
		EventBase b = new EventBase();
		
		EnergyConsumptionRule ecr = new EnergyConsumptionRule();
		
		EnergyConsumption energyConsumption = new EnergyConsumption();
		
		energyConsumption.putproperty("type", "energy consumption");
		energyConsumption.putproperty("room", "401");
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