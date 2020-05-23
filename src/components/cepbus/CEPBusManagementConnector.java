package components.cepbus;

import components.interfaces.CEPBusManagementCI;
import fr.sorbonne_u.components.connectors.AbstractConnector;

public class CEPBusManagementConnector 
extends AbstractConnector
implements CEPBusManagementCI
{

	@Override
	public String getEventReceptionInboundPortURI(String uri) {
		return ((CEPBusManagementCI)this.offering).getEventReceptionInboundPortURI(uri);
	}

	@Override
	public void registerEventReceptor(String uri, String inboundPortURI) {
		((CEPBusManagementCI)this.offering).registerEventReceptor(uri, inboundPortURI);
	}

	@Override
	public void unregisterEventReceptor(String uri) {
		((CEPBusManagementCI)this.offering).unregisterEventReceptor(uri);
	}

	@Override
	public void registerCommandExecutor(String uri, String inboundPortURI) {
		((CEPBusManagementCI)this.offering).registerCommandExecutor(uri, inboundPortURI);
	}

	@Override
	public String getExecutorInboundPortURI(String executorURI) {
		return ((CEPBusManagementCI)this.offering).getExecutorInboundPortURI(executorURI);
	}

	@Override
	public void unregisterCommandExecutor(String uri) {
		((CEPBusManagementCI)this.offering).unregisterCommandExecutor(uri);
	}

}
