package ports;

import components.interfaces.CEPBusManagementCI;
import components.interfaces.EventReceptionCI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;
import interfaces.event.EventI;

public class RegisterOutboundPort 
extends AbstractOutboundPort
implements CEPBusManagementCI
{

	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = -5951696210210052482L;

	public RegisterOutboundPort(Class<?> implementedInterface, ComponentI owner) throws Exception {
		super(implementedInterface, owner);
	}

	@Override
	public String getEventReceptionInboundPortURI(String uri) {
		return ((CEPBusManagementCI)this.connector).getEventReceptionInboundPortURI(uri);
	}

	@Override
	public void registerEventReceptor(String uri, String inboundPortURI) {
		((CEPBusManagementCI)this.connector).registerEventReceptor(uri,inboundPortURI);
	}

	@Override
	public void unregisterEventReceptor(String uri) {
		((CEPBusManagementCI)this.connector).unregisterEventReceptor(uri);
	}

	@Override
	public void registerCommandExecutor(String uri, String inboundPortURI) {
		((CEPBusManagementCI)this.connector).registerCommandExecutor(uri,inboundPortURI);
	}

	@Override
	public String getExecutorInboundPortURI(String executorURI) {
		return ((CEPBusManagementCI)this.connector).getExecutorInboundPortURI(executorURI);
	}

	@Override
	public void unregisterCommandExecutor(String uri) {
		((CEPBusManagementCI)this.connector).unregisterCommandExecutor(uri);
	}

}
