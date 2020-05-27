package components.correlators;

import Rules.IntrusionRule;
import correlator.EventBase;
import correlator.RuleBase;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import interfaces.event.EventI;
import ports.CorrelatorOutboundPort;
import ports.EventReceptionInboundPort;
/**
 * 
 * presence detected et window open
 *
 */
public class IntrusionCorrelator extends AbstractComponent {

	private final String copURI = "cop_uri";
	private final String eripURI = "erip_uri";
	
	private CorrelatorOutboundPort cop;
	private EventReceptionInboundPort erip;
	
	private EventBase registeredEvents;
	private RuleBase registeredRules;
	
	protected IntrusionCorrelator() throws Exception {
		super(1, 0);
		this.registeredEvents = new EventBase();
		this.registeredRules = new RuleBase();
		this.registeredRules.addRule(new IntrusionRule());
		
		this.initialise();
	}
	
	public void initialise() throws Exception {
		this.cop = new CorrelatorOutboundPort(copURI,this);
		this.erip = new EventReceptionInboundPort(eripURI,this);
		
		this.cop.publishPort();
		this.erip.publishPort();
	}
	
	public void start() {
	}
	
	
	/*
	 * Receive events from CEPBus
	 */
	public void receiveEvent(String emitterURI, EventI e) {
		this.registeredEvents.addEvent(e);
	}
	
	public void execute() {
		if (this.registeredEvents.numberOfEvents() >= 2) {
			this.registeredRules.fireAllOn(registeredEvents);
		}
		else {
			Thread.sleep(1000L);
		}
		// pas bon pcq ExecutorCommandI expected
		this.cop.execute("activate Alarm");
	}
	
	public void finalise() throws Exception {
		this.doPortDisconnection(this.cop.getPortURI());
		this.doPortDisconnection(this.erip.getPortURI());
		super.finalise();
	}
	
	public void shutdown() throws ComponentShutdownException {
		try {
			this.cop.unpublishPort();
			this.erip.unpublishPort();
		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdown();
	}
}
