package ports;

import components.CEPBus;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import interfaces.component.CEPBusManagementCI;

public class CEPBusManagementInboundPort 
extends AbstractInboundPort
implements CEPBusManagementCI
{

	private static final long serialVersionUID = 1L;

	public CEPBusManagementInboundPort(String cepURI, ComponentI owner) throws Exception {
		super (CEPBusManagementCI.class, owner);
		assert owner instanceof CEPBus;
	}

	@Override
	public String getEventReceptionInboundPortURI(String uri) throws Exception {
		return ((CEPBusManagementCI)this.owner).getEventReceptionInboundPortURI(uri);
	}

	@Override
	public void registerEventReceptor(String uri, String inboundPortURI) throws Exception {
		((CEPBusManagementCI)this.owner).registerEventReceptor(uri, inboundPortURI);
	}

	@Override
	public void unregisterEventReceptor(String uri) throws Exception {
		((CEPBusManagementCI)this.owner).unregisterEventReceptor(uri);		
	}

	@Override
	public void registerCommandExecutor(String uri, String inboundPortURI) throws Exception {
		((CEPBusManagementCI)this.owner).registerCommandExecutor(uri,inboundPortURI);	
	}

	@Override
	public String getExecutorInboundPortURI(String executorURI) throws Exception {
		return ((CEPBusManagementCI)this.owner).getExecutorInboundPortURI(uri);	
	}

	@Override
	public void unregisterCommandExecutor(String uri) throws Exception {
		((CEPBusManagementCI)this.owner).unregisterCommandExecutor(uri);	
	}

}
