package JUnit4_Test.event;

import org.junit.Test;

import Events.Close;

public class testClose {

	@Test
	public void test() {

		Close fermer = new Close();
		Close close = new Close();

		fermer.putproperty("fermerWindow", 20.5);
		close.putproperty("closeWindow", 22.5);

		fermer.displayProperties();
		close.displayProperties();

		assert ((double) fermer.getPropertyValue("fermerWindow")) == 20.5;
		assert ((double) close.getPropertyValue("closeWindow")) == 22.5;
	}
}
