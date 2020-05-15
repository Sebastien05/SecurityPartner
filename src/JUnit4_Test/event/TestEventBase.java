package JUnit4_Test.event;

import org.junit.Test;

import Events.Close;
import correlator.EventBase;

public class testEventBase {

	@Test
	public void test(){
		EventBase b = new EventBase();
		b.getEventBase().add(new Close());
		b.getEventBase().add(new Close());
		
		Close fermer = new Close();
		Close close = new Close();
		
		fermer.putproperty("fermerWindow", 20.5) ;
		close.putproperty("closeWindow", 20.5) ;
		
		fermer.displayProperties();
		close.displayProperties();
		
		b.getEventBase().add(close);
		b.getEventBase().add(fermer);
		
		assert b.numberOfEvents() == 4;
		b.getEventBase().remove(close);
		
		assert ((double)fermer.getPropertyValue("fermerWindow")) == 20.5 ;
		assert b.numberOfEvents() == 3;
	}
}
