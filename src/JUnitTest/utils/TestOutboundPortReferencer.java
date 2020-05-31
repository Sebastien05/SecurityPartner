package JUnitTest.utils;

import org.junit.Test;

import components.correlators.managingelement.PortReferencer;
import components.physicaldevices.PresenceDetector;
import components.physicaldevices.WindowController;
import ports.CorrelatorOutboundPort;

public class TestOutboundPortReferencer {
	
	PortReferencer<String> o = 
			new PortReferencer<String>();
	
	String room1 = "room1";
	String room2 = "room2";
	
	String eventType11 = PresenceDetector.PRESENCE_DETECTED;
	String eventType12 = WindowController.OPENED_WINDOW;
	String eventType21 = PresenceDetector.NO_PRESENCE_DETECTED;
	String eventType22 = WindowController.CLOSED_WINDOW;

	CorrelatorOutboundPort cop1b, cop2b, cop3b, cop4b = null;
	String cop1 = "c1", cop2 = "c2" , cop3 = "c3", cop4 = "c4";
	
	@Test
	public void testAdd() {
				
		o.addPort(room1, eventType11, cop1);
		o.addPort(room1, eventType12, cop2);
		o.addPort(room2, eventType21, cop3);
		o.addPort(room2, eventType22, cop4);
		
		assert o.nbRoom() == 2;
		assert o.nbEventType(room1) == 2;
		assert o.nbEventType(room2) == 2;
	}
	
	@Test
	public void testRemove() {
		
		o.addPort(room1, eventType11, cop1);
		o.addPort(room1, eventType12, cop2);
		o.addPort(room2, eventType21, cop3);
		o.addPort(room2, eventType22, cop4);
		
		assert o.removeEventType(room1, eventType11);
		assert o.nbEventType(room1) == 1;
		
		assert o.nbRoom() == 2;
		
		// Attempt to remove a non existing key return false
		assert o.removeEventType(room1, eventType11) == false;
		
		// remove all correlators remove also the room
		assert o.removeEventType(room2, eventType21);
		assert o.removeEventType(room2, eventType22);
		assert o.nbRoom() == 1;
	}
	
	@Test
	public void testGet() {
		
		o.addPort(room1, eventType11, cop1);
		try {
			assert o.getPort(room1, eventType11) == cop1;
		} catch (Exception e) {
			assert false;
		}
	}
	
}
