package JUnitTest.rules;

import org.junit.Test;
import Events.WindowOpen;
import Events.Presence;
import Rules.IntrusionRule;
import components.physicaldevices.PresenceDetector;
import correlator.EventBase;
import interfaces.event.AbstractAtomicEvent;

public class TestIntrusionRule {

	@Test
	public void test() {
		EventBase b = new EventBase();
		IntrusionRule ir = new IntrusionRule();
		
		// Window needs to be opened before presence for intrusion rule
		WindowOpen wo = new WindowOpen();
		Presence p = new Presence();
		
		p.putproperty(AbstractAtomicEvent.TYPE_PROPERTY, PresenceDetector.PRESENCE_DETECTED);
		p.putproperty(AbstractAtomicEvent.ROOM_PROPERTY, "401");
		wo.putproperty(AbstractAtomicEvent.TYPE_PROPERTY, "window open");
		wo.putproperty(AbstractAtomicEvent.ROOM_PROPERTY, "401");

		p.displayProperties();
		wo.displayProperties();
		
		System.out.println("compareTo value: "+ wo.getTimeStamp().compareTo(p.getTimeStamp()));
		
		b.getEventBase().add(p);
		b.getEventBase().add(wo);

		assert b.numberOfEvents() == 2;
		System.out.println(b.numberOfEvents());
		ir.executeOnTest(b);
		System.out.println(b.numberOfEvents());
		assert b.numberOfEvents() == 0;
		
	}
}
