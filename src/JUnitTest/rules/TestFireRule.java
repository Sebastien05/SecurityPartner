package JUnitTest.rules;

import org.junit.Test;

import Events.Smoke;
import Rules.FireRule;
import correlator.EventBase;

public class TestFireRule {

	@Test
	public void test() {
		EventBase b = new EventBase();
		
		FireRule fr = new FireRule();
		
		Smoke smoke = new Smoke();
		
		smoke.putproperty("type", "smoke");
		smoke.putproperty("room", "401");
		
		smoke.displayProperties();

		b.getEventBase().add(smoke);

		assert b.numberOfEvents() == 1;
		fr.executeOn(b);
		assert b.numberOfEvents() == 0;
	}
}