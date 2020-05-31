package components.correlators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import Rules.IntrusionRule;
import components.CEPBus;
import components.connectors.CEPBusManagementConnector;
import components.connectors.CorrelatorCommandConnector;
import components.correlators.managingelement.EventBase;
import components.correlators.managingelement.PortReferencer;
import components.correlators.managingelement.RuleBase;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import interfaces.component.CEPBusManagementCI;
import interfaces.component.EventReceptionCI;
import interfaces.component.ExecutorCI;
import interfaces.component.ReceptorEventI;
import interfaces.event.EventI;
import ports.CorrelatorOutboundPort;
import ports.EventReceptionInboundPort;
import ports.RegisterOutboundPort;

@OfferedInterfaces(offered={EventReceptionCI.class})
@RequiredInterfaces(required={CEPBusManagementCI.class, ExecutorCI.class})

public class IntrusionCorrelator 
extends AbstractCorrelator
{

	/** Intrusion Correlator constructor
	 * @param eripURI event reception inbound port URI
	 * @param ropURI register outbound Port
	 * @param urisToListen outboundPort uris to listen from emitter 
	 * @param iprURIs inboundPort uris referencer from executor to send them command
	 * @throws Exception
	 */
	protected IntrusionCorrelator(
		String eripURI,
		String ropURI,
		ArrayList<String> urisToListen,
		PortReferencer<String> iprURIs
		) 
	throws Exception 
	{
		super(eripURI, ropURI, urisToListen, iprURIs);
	}
	
	@Override
	public void initBis() {
		// give to IntrusionRule the PortReferencer in order to send an Executor command to alarm
		this.registeredRules.addRule(new IntrusionRule(this.opr));	
	}

	@Override
	public void	start() throws ComponentStartException
	{
		this.logMessage("starting Presence Detector component.") ;
		super.start();
	}

	@Override
	public void executeBis() {
		// Nothing here (optional)
	}
	/*
	 * Receive events from CEPBus
	 */
	@Override
	public void eventProcess(String emitterURI, EventI e) throws Exception {
		this.registeredEvents.addEvent(e);
		this.registeredRules.fireFirstOn(this.registeredEvents);
	}
		
}
