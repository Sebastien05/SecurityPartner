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
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import interfaces.component.ReceptorEventI;
import interfaces.event.EventI;
import ports.CorrelatorOutboundPort;
import ports.EventReceptionInboundPort;
import ports.RegisterOutboundPort;

public abstract class AbstractCorrelator 
extends AbstractComponent
implements ReceptorEventI
{
	protected String eripURI;
	protected String ropURI;
	protected ArrayList<String> urisToListen;
	
	protected EventReceptionInboundPort erip;
	protected RegisterOutboundPort rop;
	
	protected EventBase registeredEvents;
	protected RuleBase registeredRules;
	
	protected PortReferencer<CorrelatorOutboundPort> opr;
	
	protected static int cpt=0;
	protected int id;
	
	/** Intrusion Correlator constructor
	 * @param eripURI event reception inbound port URI
	 * @param ropURI register outbound Port
	 * @param urisToListen outboundPort uris to listen from emitter 
	 * @param iprURIs inboundPort uris referencer from executor to send them command
	 * @throws Exception
	 */
	protected AbstractCorrelator(
		String eripURI,
		String ropURI,
		ArrayList<String> urisToListen,
		PortReferencer<String> iprURIs
		) 
	throws Exception 
	{
		super(1, 0);
		this.eripURI = eripURI;
		this.ropURI = ropURI;
		this.urisToListen = urisToListen;
		
		this.registeredEvents = new EventBase();
		this.registeredRules = new RuleBase();	
		this.id=cpt++;
		this.init(iprURIs);
	}
	
	/** Initiate all necessaries connections
	 * @param iprURIs inboundPort uris referencer for construction of 
	 * Correlator OutboundPort Referencer to command executors
	 * @throws Exception
	 */
	public void init(PortReferencer<String> iprURIs) 
	throws Exception {
		
		this.erip = new EventReceptionInboundPort(eripURI,this);
		this.rop = new RegisterOutboundPort(ropURI, this);
		
		this.erip.publishPort();
		this.rop.publishPort();
		
		// Make connections and Correlator OutboundPort Referencer
		this.doExecutorConnection(iprURIs);
		
		// Add Rules
		this.initBis();
	
		// connection with the management port 
		this.doPortConnection(this.rop.getPortURI(), CEPBus.INBOUND_PORT_MANAGEMENT_URI,
				CEPBusManagementConnector.class.getCanonicalName());
		
	}
	// InitBis in order to add specific rules 
	public abstract void initBis();
	
	/** Make a Correlator OutboundPort Referencer with an URI InboundPort Referencer
	 * @param hash data structure {key : room, value : {key : class emitter, value : executorInboundPortURI}}
	 * @throws Exception
	 */
	public void doExecutorConnection(
		PortReferencer<String> hash
		)
	throws Exception
	{
		// Initialize OutboundPortReferencer
		this.opr = new PortReferencer<CorrelatorOutboundPort>();
		
		// Create and Add port connection in out port referencer
		for(Entry<String, HashMap<String, String>> room: hash.getHash().entrySet()) {
			for(Entry<String, String> reference: room.getValue().entrySet()) {
				// create out port for executor command
				CorrelatorOutboundPort port = new CorrelatorOutboundPort(this);
				// publish it
				port.publishPort();
				// connect it to the executorInboundPortURI
				System.out.println("EventType = "+reference.getKey());
				System.out.println("Do port connection with : "+port.getPortURI()+" -> "+reference.getValue());
				
				this.doPortConnection(port.getPortURI(), reference.getValue(),
									  CorrelatorCommandConnector.class.getCanonicalName());
				// Add it to OutboundPOrtReferencer
				this.opr.addPort(room.getKey(), reference.getKey(), port);
				// {key : room, value { key : class emitter, value : CorrelatorOutboundPort}}
			}
		}
	}
	@Override
	public void	start() throws ComponentStartException
	{
		this.logMessage("starting Presence Detector component.") ;
		super.start();
	}
	/**
	 * Connect port for event reception and send executor command 
	 */
	@Override
	public void execute() throws Exception {
		
		// Register for all uris to listen
		for (String uriToListen : this.urisToListen) {
			this.rop.registerEventReceptor(uriToListen, this.eripURI);
		}
		this.executeBis();
	}
	/**
	 * Method to implement in a concrete correlator
	 */
	public abstract void executeBis();
	
	@Override
	public void finalise() throws Exception {
		
		this.doPortDisconnection(this.rop.getPortURI());

		for (CorrelatorOutboundPort cop : this.opr.getAllPort()) {
			this.doPortDisconnection(cop.getPortURI());
		}
		
		super.finalise();
	}
	@Override
	public void shutdown() 
	throws ComponentShutdownException {
		try {
			this.rop.unpublishPort();
			this.erip.unpublishPort();
			for (CorrelatorOutboundPort cop : this.opr.getAllPort()) {
				cop.unpublishPort();
			}
		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdown();
	}
	

}
