package ports;

import components.interfaces.EventEmissionCI;
import components.interfaces.EventReceptionCI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import interfaces.event.EventI;

public class AlarmeInboundPort  extends AbstractInboundPort implements EventReceptionCI{

	public AlarmeInboundPort(Class<?> implementedInterface, ComponentI owner) throws Exception {
		super (EventEmissionCI.class, owner);
		assert	owner instanceof EventEmissionCI ;
	}
	
	public AlarmeInboundPort( String uri,ComponentI owner) throws Exception {
		super (EventReceptionCI.class, owner);
		assert	owner instanceof EventEmissionCI ;
	}
	private static final long serialVersionUID = 1L;

	@Override
	public void receiveEvent(String emitterURI, EventI e) throws Exception {
		// TODO Auto-generated method stub
		
	}
	

}
