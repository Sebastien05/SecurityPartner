package components;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ArrayList;

import components.connectors.CEPBusEventEmissionConnector;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import interfaces.component.CEPBusManagementCI;
import interfaces.component.EventEmissionCI;
import interfaces.component.EventReceptionCI;
import interfaces.component.ReceptorEventI;
import interfaces.event.AbstractAtomicEvent;
import interfaces.event.EventI;
import ports.CEPBusManagementInboundPort;
import ports.EventEmissionOutboundPort;
import ports.EventReceptionInboundPort;

@OfferedInterfaces(offered={CEPBusManagementCI.class, EventReceptionCI.class})
@RequiredInterfaces(required={EventEmissionCI.class})

public class CEPBus extends AbstractComponent implements ReceptorEventI {

	//protected CEPBusManagementInboundPort managementInPort;
	//protected EventReceptionInboundPort eventInPort;
	
	public static final String					INBOUND_PORT_MANAGEMENT_URI  = "ipmURI" ;
	public static final String					INBOUND_PORT_EVENT_RECEPTION_URI = "iperURI" ;
	protected CEPBusManagementInboundPort               cbmip ;
	protected EventReceptionInboundPort				    erip ;

	
	// received event indexed by their emmitter
	private HashMap<String,ArrayList<EventI>> receivedEvent = new HashMap<String,ArrayList<EventI>>();
	
	// registered correlator indexed by their inboundPort uri with 
	private HashMap<String,ArrayList<String>> registeredCorrelator = new HashMap<String, ArrayList<String>>();
	
	// registered physical devices indexed by their uri with value inboundPortURI from Bus
	private HashMap<String,String> registeredPhysicalDevices = new HashMap<String, String>();
	
	// correlatorInboundPort URI redirection : {Key : inboundPortURI, value : cepBusOutboundPort}
	private HashMap<String, EventEmissionOutboundPort> outboundPortConnexion = new HashMap<>();

	
	protected CEPBus() 
	throws Exception 
	{
		super(1, 0);
		this.initialise() ;
	}

	protected void initialise() 
	throws Exception 
	{
		/*
		 * Create and publish Management and EventReception inboundPort
		 */
		
		this.cbmip = new CEPBusManagementInboundPort(INBOUND_PORT_MANAGEMENT_URI, this) ;
		this.cbmip.publishPort() ;
		
		this.erip = new EventReceptionInboundPort(INBOUND_PORT_EVENT_RECEPTION_URI, this) ;
		this.erip.publishPort() ;
	}
    
	
	/**
	 * Receive Event from EventReceptionInboundPort
	 * @param emitterURI
	 * @param e
	 * @throws Exception 
	 */
	
	public void eventProcess(String emitterURI, EventI e) throws Exception {
		/*
		 * we'll stock emitter uri and all its events in the HashMap
		 */
		
		ArrayList<String> destinationURIs = new ArrayList<>();
		
		for(Entry<String, ArrayList<String>> entry: registeredCorrelator.entrySet()) {
			if (entry.getValue().contains(emitterURI)) {
				destinationURIs.add(entry.getKey());
			}
		}
		this.multisendEvent(emitterURI, destinationURIs, e);
		
	}
	
	//Les fonction suivantes viennents de CEPBusManagementCI
	public String getEventReceptionInboundPortURI(String uri) throws Exception{
		// quand un physical device appelle cette méthode
		// le bus crée un port in pour aceuillir ses events
		// il le publish ensuite
		// Et renvoie l'uri du in créée à savoir uri+"-inboundPortBus"
		// Le physical device ayant recu l'uri du in il pourra donc 
		// s'y connecter avec un doPortConnection
		return INBOUND_PORT_EVENT_RECEPTION_URI;
//		return this.registeredPhysicalDevices.get(uri);
	}

	/**
	 * Register an URI to inboundPortURI in order to redirect the events
	 * @param uri
	 * @param inboundPortURI
	 *  
	 */
	
	public void registerEventReceptor(String uri, String inboundPortURI) throws Exception {
		/*
		 * 1. Get the current component uri list
		 * 2. Add uri to listen
		 * 3. Update the uri list for inboundPortURI key
		 */
		ArrayList<String> uriList = new ArrayList<>();
		if (this.registeredCorrelator.containsKey(inboundPortURI)) {
			uriList = this.registeredCorrelator.get(inboundPortURI);
		} else {
			
			// First time registration //
						
			// Create an out port bus for the inboundPortURI
			String outboundPortURI = "correlator-"+outboundPortConnexion.size();
			EventEmissionOutboundPort newPort = new EventEmissionOutboundPort(outboundPortURI, this);
			
			// Publish it
			newPort.publishPort() ;
			
			System.out.println("\n(CEPBus) CONNECTION WITH :: "+inboundPortURI+"\n");
			
			// doPortConnection with out port uri created and the inboundPortURI
			this.doPortConnection(outboundPortURI, inboundPortURI,
					CEPBusEventEmissionConnector.class.getCanonicalName());
			
			// Add to the outboundPortConnexion HashMap {Key : correlatorInboundPortURI, Value : cepBusOutboundPort}
			outboundPortConnexion.put(inboundPortURI, newPort);
		
		}
		// add the uri to listen and put this arrayList in the registeredCorrelator
		uriList.add(uri);
		this.registeredCorrelator.put(inboundPortURI, uriList);
		
		System.out.println("Size outboundPortConnexion :"+registeredCorrelator.get(inboundPortURI).size());
	}
	
	/**
	 * Unregister correlator 
	 * @param uri
	 * 
	 */
	public void unregisterEventReceptor(String uri) throws Exception {
		
		// remove correlator uri key and his value with all related physical devices to listen
		registeredCorrelator.remove(uri);
		
		// get the corresponding outboundPort 
		EventEmissionOutboundPort portToDelete = outboundPortConnexion.get(uri);
		
		// unpublish it
		portToDelete.unpublishPort();
		
		// And remove it from the HashMap
		outboundPortConnexion.remove(uri);
	}
	
	/**
	 * Send event from emmitterURI to destinationURI
	 * @param emitterURI
	 * @param destinationURI
	 * @param e
	 */
	public void sendEvent(String emitterURI, String destinationURI, EventI e) throws Exception {
        

		// get the out port to redirect the event
		EventEmissionOutboundPort portToReceiveEvent = outboundPortConnexion.get(destinationURI);
		// send event on this port
		portToReceiveEvent.sendEvent(emitterURI, destinationURI, e);
	}

	/**
	 * Call sendEvent to redirect Event for all destinationURIs
	 */
	public void multisendEvent(String emitterURI, List<String> destinationURIs, EventI e) throws Exception {
        
		System.out.println(" o-> [ Receive Event in CEPBus ]");
		((AbstractAtomicEvent) e).displayProperties();
		for (String destinationURI : destinationURIs) {
			sendEvent(emitterURI, destinationURI, e);
		}
		
	}
	
	/*
	 * These methods are not used
	 * 
	 * We give all corresponding executor uri port directly to the correlator
	 *
	 */
	
	public void registerCommandExecutor(String uri, String inboundPortURI)
	throws Exception{}

	public String getExecutorInboundPortURI(String executorURI)
	throws Exception {return null;}

	public void unregisterCommandExecutor(String uri)
	throws Exception{}
	
}
