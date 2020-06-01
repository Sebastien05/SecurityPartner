package components.correlators;

import java.util.ArrayList;

import Rules.HeatingOptimizerRule;
import components.correlators.managingelement.PortReferencer;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import interfaces.event.EventI;

public class HeatingOptimizerCorrelator 
extends AbstractCorrelator
{


	/** Intrusion Correlator constructor
	 * @param eripURI event reception inbound port URI
	 * @param ropURI register outbound Port
	 * @param urisToListen outboundPort uris to listen from emitter 
	 * @param iprURIs inboundPort uris referencer from executor to send them command
	 * @throws Exception
	 */
	protected HeatingOptimizerCorrelator(
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
		// give to HeatingOptimizerRule the PortReferencer in order 
		// to send an Executor command to the thermostat
		this.registeredRules.addRule(new HeatingOptimizerRule(this.opr));	
	}

	@Override
	public void	start() throws ComponentStartException
	{
		this.logMessage("starting Heating Optimizer Correlator.") ;
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
