package ports;

import components.interfaces.EventEmissionCI;
import components.interfaces.EventReceptionCI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import interfaces.event.EventI;
import interfaces.executor.ExecutorCI;
import interfaces.executor.ExecutorCommandI;

public class AlarmeInboundPort  
extends AbstractInboundPort 
implements ExecutorCI{

	private static final long serialVersionUID = 1L;
	
	public AlarmeInboundPort(ComponentI owner) throws Exception {
		super (EventEmissionCI.class, owner);
		assert	owner instanceof ExecutorCI ;
	}
	
	public AlarmeInboundPort( String uri,ComponentI owner) throws Exception {
		super (EventReceptionCI.class, owner);
		assert	owner instanceof ExecutorCI ;
	}

	@Override
	public void execute(ExecutorCommandI command) {
		((ExecutorCI)this.owner).execute(command);
	}
	

}
