package components.connectors;

import fr.sorbonne_u.components.connectors.AbstractConnector;
import interfaces.component.CEPBusManagementCI;

public class CEPBusManagementConnector 
extends AbstractConnector
implements CEPBusManagementCI
{

	@Override
	public String getEventReceptionInboundPortURI(String uri) throws Exception{
		return ((CEPBusManagementCI)this.offering).getEventReceptionInboundPortURI(uri);
	}

	@Override
	public void registerEventReceptor(String uri, String inboundPortURI) throws Exception{
		((CEPBusManagementCI)this.offering).registerEventReceptor(uri, inboundPortURI);
	}

	@Override
	public void unregisterEventReceptor(String uri) throws Exception{
		((CEPBusManagementCI)this.offering).unregisterEventReceptor(uri);
	}

	@Override
	public void registerCommandExecutor(String uri, String inboundPortURI) throws Exception{
		((CEPBusManagementCI)this.offering).registerCommandExecutor(uri, inboundPortURI);
	}

	@Override
	public String getExecutorInboundPortURI(String executorURI) throws Exception{
		return ((CEPBusManagementCI)this.offering).getExecutorInboundPortURI(executorURI);
	}

	@Override
	public void unregisterCommandExecutor(String uri) throws Exception{
		((CEPBusManagementCI)this.offering).unregisterCommandExecutor(uri);
	}

}
