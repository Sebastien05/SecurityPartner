package ports;

import components.cepbus.CEPBus;
import components.interfaces.CEPBusManagementCI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

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
	public String getEventReceptionInboundPortURI(String uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registerEventReceptor(String uri, String inboundPortURI) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unregisterEventReceptor(String uri) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerCommandExecutor(String uri, String inboundPortURI) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getExecutorInboundPortURI(String executorURI) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void unregisterCommandExecutor(String uri) {
		// TODO Auto-generated method stub
		
	}

}
