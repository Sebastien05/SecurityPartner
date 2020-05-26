package components.connectors;

import fr.sorbonne_u.components.connectors.AbstractConnector;
import interfaces.executor.ExecutorCI;
import interfaces.executor.ExecutorCommandI;

public class CorrelatorCommandConnector 
extends AbstractConnector 
implements ExecutorCI{
	
	protected void execute(ExecutorCommandI command) {
		((ExecutorCI)this.offering).execute(command);
	}
}
