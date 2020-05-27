package ports;

import components.CEPBus;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import interfaces.component.CEPBusManagementCI;
import interfaces.component.EventReceptionCI;
import interfaces.event.EventI;

public class EventReceptionInboundPort extends AbstractInboundPort
implements EventReceptionCI
{

	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = 1788634705746785504L;

	public EventReceptionInboundPort(String cepURI, ComponentI owner) 
	throws Exception 
	{
		super (CEPBusManagementCI.class, owner);
		assert owner instanceof CEPBus;
	}

	@Override
	public void receiveEvent(String emitterURI, EventI e) throws Exception {
		try {
			((CEPBus) this.getOwner()).receiveEvent(emitterURI, e);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
//		this.getOwner().handleRequestSync( o -> ((CEPBus)o).receiveEvent(emitterURI, e));
	}

}
