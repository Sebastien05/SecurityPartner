package ports;

import components.interfaces.EventEmissionCI;
import components.interfaces.EventReceptionCI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;
import interfaces.event.EventI;

public class RegisterOutboundPort extends AbstractOutboundPort
implements EventReceptionCI
{

	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = -5951696210210052482L;

	public RegisterOutboundPort(Class<?> implementedInterface, ComponentI owner) throws Exception {
		super(implementedInterface, owner);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void receiveEvent(String emitterURI, EventI e) throws Exception{
		((EventReceptionCI)this.connector).receiveEvent(emitterURI, e);
	}

}
