package JUnitTest.event;

import org.junit.Test;

import Events.Window;
import components.physicaldevices.WindowController;

public class TestAtomicEvent {

	@Test
	public void test() {

		String room1 = "chambre1";
		String room2 = "chambre2";
		
		Window opened = new Window(room1);
		Window closed = new Window(room2);

		opened.putproperty(Window.TYPE_PROPERTY, WindowController.OPENED_WINDOW) ;
		closed.putproperty(Window.TYPE_PROPERTY, WindowController.CLOSED_WINDOW);

		assert ((String)opened.getPropertyValue(Window.TYPE_PROPERTY)) == WindowController.OPENED_WINDOW  ;
		assert ((String)closed.getPropertyValue(Window.TYPE_PROPERTY)) == WindowController.CLOSED_WINDOW  ;
	}
}
