package components.connectors;

import fr.sorbonne_u.components.connectors.AbstractConnector;
import interfaces.component.ExecutorCI;
import interfaces.component.ExecutorCommandI;

public class CorrelatorCommandConnector 
extends AbstractConnector 
implements ExecutorCI{
	
	public void execute(ExecutorCommandI<?> command) throws Exception {
		((ExecutorCI)this.offering).execute(command);
	}
}