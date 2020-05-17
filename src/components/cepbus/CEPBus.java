package components.cepbus;

import java.util.HashMap;

import java.util.Map.Entry;
import java.util.ArrayList;

import components.interfaces.CEPBusManagementCI;
import components.interfaces.EventReceptionCI;
import components.interfaces.EventEmissionCI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import interfaces.event.EventI;
import ports.CEPBusManagementInboundPort;
import ports.EventEmissionOutboundPort;
import ports.RegisterOutboundPort;

@OfferedInterfaces(offered={CEPBusManagementCI.class, EventReceptionCI.class})
@RequiredInterfaces(required={EventEmissionCI.class})

public class CEPBus extends AbstractComponent implements EventEmissionCI{

	//protected CEPBusManagementInboundPort managementInPort;
	//protected EventReceptionInboundPort eventInPort;
	
	public static final String					INBOUND_PORT_URI = "cbmipURI" ;
	protected EventEmissionOutboundPort				    eeobp ;
	protected CEPBusManagementInboundPort               cbmip ;

	
	//{key: emetteur, value: listeDestinataires}
	private HashMap<String,ArrayList<EventI>> receivedEvent = new HashMap<String,ArrayList<EventI>>();
	//{key: destinataire, value:[{EmetteurURI_1:[event1,event2,event3]},{EmetteurURI_2:[event2]}]}
	private HashMap<String,ArrayList<HashMap<String,ArrayList<EventI>>>> eventToSend = new HashMap<String,ArrayList<HashMap<String,ArrayList<EventI>>>>();
	
	
	protected CEPBus(String cepURI,  ComponentI owner) throws Exception {
		super(1, 0);
		this.initialise() ;
		//this.managementInPort = new CEPBusManagementInboundPort(cepURI, this);
		//this.eventInPort = new EventReceptionInboundPort(cepURI, this);
	}
	
	protected CEPBus(String reflectionInboundPortURI)
			throws Exception
			{
				super(reflectionInboundPortURI, 1, 0) ;
				this.initialise() ;
			}


	protected void initialise() throws Exception {
		this.cbmip = this.createPort() ;
		this.cbmip.publishPort() ;
		
		this.eeobp = new EventEmissionOutboundPort(this);
		this.eeobp.publishPort() ;
	}
	
	protected CEPBusManagementInboundPort createPort() throws Exception {
		return new CEPBusManagementInboundPort(INBOUND_PORT_URI, this) ;
	}
    
	public HashMap<String,ArrayList<HashMap<String,ArrayList<EventI>>>> getEventToSend(){
		return eventToSend;
	}
	
	//La fonction suivante viens de EventReceptionCI 
	/**
	 * recevoir un event . cf: fin page 8 du cahier des charges
	 * @param emitterURI
	 * @param e
	 */
	//on va stocker l'event et uri de l'emetter dans une hashmap
	public void receiveEvent(String emitterURI, EventI e) {
		//si il n'y a aucun event reçu et aucun enregistrement
		//ou si l'uri de l'emetteur n'est pas contenu
		if (receivedEvent.size()==0 || !receivedEvent.containsKey(emitterURI)) {
			ArrayList<EventI> listeDestinataires = new ArrayList<>();
			receivedEvent.put(emitterURI, listeDestinataires);
		}
		
		if(receivedEvent.containsKey(emitterURI)){// verification en plus
			for(Entry<String, ArrayList<EventI>> entry: receivedEvent.entrySet()) {
				if (entry.getKey().equals(emitterURI)) {
					entry.getValue().add(e);
				}
			}
		}
		System.out.println();
	}
	
	//Les fonction suivantes viennents de CEPBusManagementCI
	public String getEventReceptionInboundPortURI(String uri) {
		
		return null;
	}

	public void registerEventReceptor(String uri, String inboundPortURI) {
		
	}
	
	/**
	 * Deenregistrer les commandes 
	 * @param uri
	 */
	public void unregisterEventReceptor(String uri) {
		
	}

	public void registerCommandExecutor(String uri, String inboundPortURI) {
		
	}

	public String getExecutorInboundPortURI(String executorURI) {
		return null;
	}
	/**
	 * deenregistre une commande avec l'uri d'un executeur
	 * @param uri
	 */
	public void unregisterCommandExecutor(String uri) {
		
	}
	
	
	
	//les deux fonctions suivante viennent de EventEmissionsCI(regroupe les opérations par lesquelles il va transmettre les événements aux entités qui doivent les traiter)
	//appeler par les composants physiques 
	
	
	/**
	 * envoyer un evenement provenant de l'URI d'un emetteur(emitterURI) contenu dans le bus 
	 */
	public void sendEvent(String emitterURI, String destinationURI, EventI e) throws Exception {
        //on trie la liste par ordre d'arriver avec timestamp
		if (eventToSend.size()==0) {
			System.out.println("Nothing to send !");
		}
		
		//on construit en meme temps la liste pour send
		ArrayList<EventI> events = new ArrayList<>();
		events.add(e);//ex:[event1] puis [events1,events2]
		
		HashMap<String,ArrayList<EventI>> eEvents = new HashMap<String,ArrayList<EventI>>();
		eEvents.put(emitterURI, events);//ex:{EmetteurURI_1:[event1,event2,event3]}
		//eEvent.put() //avec timeStamp pour les comparer
		
		ArrayList<HashMap<String, ArrayList<EventI>>> emittersEventsT = new ArrayList<>();
		emittersEventsT.add(eEvents); //ex:value:[{EmetteurURI_1:[event1,event2,event3]},{EmetteurURI_2:[event2]}]
		
		eventToSend.put(e.getURI(),emittersEventsT); //ex: {key: destinataire, value:[{EmetteurURI_1:[event1,event2,event3]},{EmetteurURI_2:[event2]}]}
	
		//on prend la valeur correspond au destinataire
		ArrayList<HashMap<String, ArrayList<EventI>>> destinataire = eventToSend.get(destinationURI);
		
		//destinataire.
		//for (Entry<String, ArrayList<HashMap<String, ArrayList<EventI>>>> elt: eventToSend.entrySet()) {
			
		//}
		System.out.print(destinataire);
		//comment on send ?
	}
	
	/**
	 * envoyer plusieurs evenement provenant de l'URI d'un emetteur(emitterURI) contenu dans le bus 
	 */
	public void multisendEvent(String emitterURI, String[] destinationURIs, EventI e) throws Exception {
	      
	}
	
	/*public static void main (String[] args) {
		//CEPBus cep = new CEPBus();
	}*/
}
