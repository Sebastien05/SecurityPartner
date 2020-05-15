package JUnitTest.rules;

import org.junit.Test;
import Events.WindowOpen;
import Events.Presence;
import Rules.IntrusionRule;

import correlator.EventBase;

public class TestIntrusionRule {

	@Test
	public void test() {
		EventBase b = new EventBase();
		IntrusionRule ir = new IntrusionRule();
		Presence p = new Presence();
		WindowOpen wo = new WindowOpen();
		
		p.putproperty("type", "presence detection");
		p.putproperty("room", "401");
		wo.putproperty("type", "window open");
		wo.putproperty("room", "401");

		b.getEventBase().add(p);
		b.getEventBase().add(wo);

		assert b.numberOfEvents() == 2;
		ir.executeOn(b);
		assert b.numberOfEvents() == 0;
	}
}
