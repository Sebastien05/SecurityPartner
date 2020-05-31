package JUnitTest.rules;

import org.junit.Test;

import Events.Smoke;
import Rules.FireRule;
import components.correlators.managingelement.EventBase;
import interfaces.event.AbstractAtomicEvent;

public class TestFireRule {

	@Test
	public void test() {
		EventBase b = new EventBase();
		
		FireRule fr = new FireRule();
		
		Smoke smoke = new Smoke("401");
		
		smoke.putproperty(AbstractAtomicEvent.TYPE_PROPERTY, "smoke");
		
		smoke.displayProperties();

		b.getEventBase().add(smoke);

		assert b.numberOfEvents() == 1;
		try {
			fr.executeOn(b);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assert b.numberOfEvents() == 0;
	}
}