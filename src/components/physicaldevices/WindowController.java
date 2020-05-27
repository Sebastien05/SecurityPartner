package components.physicaldevices;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import interfaces.component.EventEmissionCI;
import ports.EventEmissionOutboundPort;
import ports.RegisterOutboundPort;

@RequiredInterfaces(required={EventEmissionCI.class})
public class WindowController extends AbstractComponent {

	
	private EventEmissionOutboundPort eeop;
	private RegisterOutboundPort rop;
	
	protected WindowController() {
		super(1, 0);
	}
}