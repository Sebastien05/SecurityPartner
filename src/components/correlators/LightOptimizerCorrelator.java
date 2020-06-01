package components.correlators;

import java.util.ArrayList;
import Rules.LightOptimisationRule;
import components.correlators.managingelement.PortReferencer;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import interfaces.component.CEPBusManagementCI;
import interfaces.component.EventReceptionCI;
import interfaces.component.ExecutorCI;
import interfaces.event.EventI;


@OfferedInterfaces(offered={EventReceptionCI.class})
@RequiredInterfaces(required={CEPBusManagementCI.class, ExecutorCI.class})

public class LightOptimizerCorrelator 
extends AbstractCorrelator
{

	/** Intrusion Correlator constructor
	 * @param eripURI event reception inbound port URI
	 * @param ropURI register outbound Port
	 * @param urisToListen outboundPort uris to listen from emitter 
	 * @param iprURIs inboundPort uris referencer from executor to send them command
	 * @throws Exception
	 */
	protected LightOptimizerCorrelator(
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
		this.registeredRules.addRule(new LightOptimisationRule(this.opr));	
	}

	@Override
	public void	start() throws ComponentStartException
	{
		this.logMessage("starting LightOptimizer correlator component.") ;
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
