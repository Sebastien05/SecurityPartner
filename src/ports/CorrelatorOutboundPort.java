package ports;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;
import interfaces.component.ExecutorCI;
import interfaces.component.ExecutorCommandI;

public class CorrelatorOutboundPort 
extends AbstractOutboundPort
implements ExecutorCI{

private static final long serialVersionUID = 1L;
	
	public CorrelatorOutboundPort(ComponentI owner) throws Exception {
		super (ExecutorCI.class, owner);
		assert	owner instanceof ExecutorCI ;
	}
	
	public CorrelatorOutboundPort( String uri,ComponentI owner) throws Exception {
		super (ExecutorCI.class, owner);
		assert	owner instanceof ExecutorCI ;
	}

	@Override
	public void execute(ExecutorCommandI<?> command) throws Exception {
		((ExecutorCI)this.connector).execute(command);
	}

}
