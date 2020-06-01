package JUnitTest.event;

import org.junit.Test;

import Events.Window;
import components.correlators.managingelement.EventBase;
import components.physicaldevices.WindowController;

public class TestEventBase {

	@Test
	public void test(){
		EventBase b = new EventBase();
		String room1 = "chambre1";
		String room2 = "chambre2";
		
		b.getEventBase().add(new Window(room1));
		b.getEventBase().add(new Window(room2));
		
		Window opened = new Window(room1);
		Window closed = new Window(room2);
		
		opened.putproperty(Window.TYPE_PROPERTY, WindowController.OPENED_WINDOW) ;
		closed.putproperty(Window.TYPE_PROPERTY, WindowController.OPENED_WINDOW);
		
		opened.displayProperties();
		closed.displayProperties();
		
		b.getEventBase().add(opened);
		b.getEventBase().add(closed);
		
		assert b.numberOfEvents() == 4;
		b.getEventBase().remove(opened);
		
		assert ((String)opened.getPropertyValue(Window.TYPE_PROPERTY)) == WindowController.OPENED_WINDOW  ;
		assert b.numberOfEvents() == 3;
	}
}
