package components.correlators;

import java.util.ArrayList;
import Rules.IntrusionRule;
import components.CEPBus;
import components.connectors.CEPBusManagementConnector;
import components.connectors.CorrelatorCommandConnector;
import components.correlators.managingelement.EventBase;
import components.correlators.managingelement.RuleBase;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import interfaces.component.CEPBusManagementCI;
import interfaces.component.EventReceptionCI;
import interfaces.component.ExecutorCI;
import interfaces.event.EventI;
import ports.CorrelatorOutboundPort;
import ports.EventReceptionInboundPort;
import ports.RegisterOutboundPort;

@OfferedInterfaces(offered={EventReceptionCI.class})
@RequiredInterfaces(required={CEPBusManagementCI.class, ExecutorCI.class})

public class IntrusionCorrelator 
extends AbstractComponent
implements EventReceptionCI
{
	// executor inbound port URI	
	private String eipURI;
	// event reception inbound port URI
	private String eripURI;
	// register outbound Port
	private String ropURI;
	private ArrayList<String> urisToListen;
	
	private CorrelatorOutboundPort cop;
	private EventReceptionInboundPort erip;
	private RegisterOutboundPort rop;
	
	private EventBase registeredEvents;
	private RuleBase registeredRules;
	
	private static int cpt=0;
	private int id;
	
	protected IntrusionCorrelator(
		String eipURI,
		String eripURI,
		String ropURI,
		ArrayList<String> urisToListen
		) 
	throws Exception 
	{
		super(1, 0);
		this.eipURI = eipURI;
		this.eripURI = eripURI;
		this.ropURI = ropURI;
		this.urisToListen = urisToListen;
		this.registeredEvents = new EventBase();
		this.registeredRules = new RuleBase();	
		this.id=cpt++;
		this.init();
	}
	
	public void init() 
	throws Exception {
		
//		System.out.println(eripURI+" - "+ropURI);
		this.cop = new CorrelatorOutboundPort("intrusion-"+id,this);
		this.erip = new EventReceptionInboundPort(eripURI,this);
		this.rop = new RegisterOutboundPort(ropURI, this);
		
		this.cop.publishPort();
		this.erip.publishPort();
		this.rop.publishPort();
		
		// give to IntrusionRule the out port in order to send an Executor command to alarm
		this.registeredRules.addRule(new IntrusionRule(this.cop));
	
//		System.out.println("Register OutbounfPort URI :: "+this.rop.getPortURI());
		// connection with the management port 
		this.doPortConnection(this.rop.getPortURI(), CEPBus.INBOUND_PORT_MANAGEMENT_URI,
				CEPBusManagementConnector.class.getCanonicalName());
		
//		System.out.println("Correlator OutboundPort URI :: "+this.cop.getPortURI());
		// connection with the executor
		this.doPortConnection(this.cop.getPortURI(), this.eipURI,
				CorrelatorCommandConnector.class.getCanonicalName());
	}
	@Override
	public void	start() throws ComponentStartException
	{
		this.logMessage("starting Presence Detector component.") ;
		super.start();
	}
	/*
	 * Connect port for event reception and send executor command 
	 */
	@Override
	public void execute() throws Exception {
		
//		System.out.println("In Execute /!\\");
//		System.out.println("Size urisToListen = "+this.urisToListen.size());
//
//		System.out.println("Event Reception InboundPort URI:: "+this.erip.getPortURI());

		// Register for all uris to listen
		for (String uriToListen : this.urisToListen) {
			System.out.println("Register request -> "+uriToListen);
			this.rop.registerEventReceptor(uriToListen, this.eripURI);
		}
	}
	/*
	 * Receive events from CEPBus
	 */
	public void receiveEvent(String emitterURI, EventI e) throws Exception {
		this.registeredEvents.addEvent(e);
		this.registeredRules.fireFirstOn(this.registeredEvents);
	}
	
	public void finalise() throws Exception {
		this.doPortDisconnection(this.cop.getPortURI());
//		this.doPortDisconnection(this.erip.getPortURI());
		super.finalise();
	}
	
	public void shutdown() 
	throws ComponentShutdownException {
		try {
			this.cop.unpublishPort();
			this.erip.unpublishPort();
		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdown();
	}
	
//
//		private ArrayList<String> urisExecutor;
//		
//		private HashMap<String, CorrelatorOutboundPort> cop;
//	
//		protected IntrusionCorrelator(
//			String copURI,
//			String eripURI,
//			String ropURI,
//			ArrayList<String> urisToListen,
//			ArrayList<String> urisExecutor
//			) 
//		throws Exception 
//		{
//			super(1, 0);
//			this.eripURI = eripURI;
//			this.ropURI = ropURI;
//			this.urisToListen = urisToListen;
//			this.urisExecutor = urisExecutor;
//			this.registeredEvents = new EventBase();
//			this.registeredRules = new RuleBase();		
//			this.init();
//		}
//		
//		public void init() 
//		throws Exception {
//			
//			this.erip = new EventReceptionInboundPort(eripURI,this);
//			this.rop = new RegisterOutboundPort(ropURI, this);
//			
//			this.erip.publishPort();
//			this.rop.publishPort();
//			
//			this.cop = new HashMap<>();
//			
//			for (String executorURI : this.urisExecutor) {
//				this.cop.put(executorURI, new CorrelatorOutboundPort(this));
//			}
//			
}
