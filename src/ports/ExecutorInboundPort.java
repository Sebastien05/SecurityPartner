package ports;	

import components.physicaldevices.AlarmComponent;
import fr.sorbonne_u.components.ComponentI;	
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import interfaces.component.ExecutorCI;
import interfaces.component.ExecutorCommandI;	

public class ExecutorInboundPort  	
extends AbstractInboundPort 	
implements ExecutorCI{	

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
	public void execute(ExecutorCommandI<AlarmComponent> command) throws Exception {
		((AlarmComponent)this.getOwner()).execute(command);	
	}	
	
}