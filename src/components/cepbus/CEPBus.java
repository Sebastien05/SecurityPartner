package components.cepbus;

import components.interfaces.CEPBusManagementCI;
import components.interfaces.EventReceptionCI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import interfaces.event.EventI;
import ports.CEPBusManagementInboundPort;
import ports.EventReceptionInboundPort;

@OfferedInterfaces(offered={CEPBusManagementCI.class, EventReceptionCI.class})
//@RequiredInterface(required={})

public class CEPBus extends AbstractComponent {

	protected CEPBusManagementInboundPort managementInPort;
	protected EventReceptionInboundPort eventInPort;
	
	
	protected CEPBus(String cepURI) 
	throws Exception 
	{
		super(1, 0);
		this.managementInPort = new CEPBusManagementInboundPort(cepURI, this);
		this.eventInPort = new EventReceptionInboundPort(cepURI, this);
	}

	public void receiveEvent(String emitterURI, EventI e) {

	}

	public String getEventReceptionInboundPortURI(String uri) {
		return null;
	}

	public void registerEventReceptor(String uri, String inboundPortURI) {
		
	}

	public void unregisterEventReceptor(String uri) {
		
	}

	public void registerCommandExecutor(String uri, String inboundPortURI) {
		
	}

	public String getExecutorInboundPortURI(String executorURI) {
		return null;
	}

	public void unregisterCommandExecutor(String uri) {
		
	}
}
