package components.connectors;

import components.physicaldevices.AlarmComponent;
import fr.sorbonne_u.components.connectors.AbstractConnector;
import interfaces.component.ExecutorCI;
import interfaces.component.ExecutorCommandI;

public class CorrelatorCommandConnector 
extends AbstractConnector
implements ExecutorCI {
	
	@Override
	public void execute(ExecutorCommandI<AlarmComponent> command) throws Exception {
		((ExecutorCI)this.offering).execute(command);
	}
}