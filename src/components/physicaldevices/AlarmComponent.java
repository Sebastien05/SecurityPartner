package components.physicaldevices;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import ports.AlarmInboundPort;
import components.interfaces.EventReceptionCI;

@OfferedInterfaces(offered={EventReceptionCI.class})
public class AlarmComponent extends AbstractComponent {
	
	protected AlarmInboundPort alarmInp;
	protected String inboundPortURI;
	
	protected AlarmComponent(String alarmInboundPortURI, int nbThreads, int nbSchedulableThreads) throws Exception {
		super(alarmInboundPortURI, 1, 1);
		this.alarmInp = new AlarmInboundPort(alarmInboundPortURI, this);
		this.alarmInp.publishPort();
	}
	
	
}

