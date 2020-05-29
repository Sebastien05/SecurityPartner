package ports;	

import fr.sorbonne_u.components.ComponentI;	
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import interfaces.component.ExecutorCI;
import interfaces.component.ExecutorCommandI;
import interfaces.component.ReceptorCommandI;	

public class ExecutorInboundPort	
extends AbstractInboundPort 	
implements ExecutorCI {	

	ComponentI owner;
	private static final long serialVersionUID = 1L;	

	public ExecutorInboundPort(ComponentI owner) throws Exception {	
		super (ExecutorCI.class, owner);
		assert	owner instanceof ExecutorCI ;	
	}	

	public ExecutorInboundPort(String uri, ComponentI owner) throws Exception {	
		super (uri, ExecutorCI.class, owner);
		assert	owner instanceof ExecutorCI ;	
	}	

	@Override	
	public void execute(ExecutorCommandI command) throws Exception {
		((ReceptorCommandI) this.getOwner()).processExecute(command);	
	}	
	
}