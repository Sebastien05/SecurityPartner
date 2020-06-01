package CVM;

import java.util.ArrayList;

import components.CEPBus;
import components.connectors.CEPBusEventEmissionConnector;
import components.correlators.IntrusionCorrelator;
import components.physicaldevices.PresenceDetector;
import components.physicaldevices.WindowController;
import components.physicaldevices.AlarmComponent;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cvm.AbstractCVM;
import fr.sorbonne_u.components.cvm.AbstractDistributedCVM;

public class MultiCVM extends AbstractDistributedCVM{
	
	// URI of the CVM instances as defined in the config.xml file
	protected static String			PROVIDER_JVM_URI = "jvm-1" ;
	protected static String			CONSUMER_JVM_URI = "jvm-2" ;
	
	protected String	uriProviderURI ;
	/** Reference to the consumer component to share between deploy
	 *  and shutdown.													*/
	protected String	uriConsumerURI ;

	
	//CEPBUS
	protected static final String	CONSUMER_COMPONENT_URI = "my-URI-cepbus" ;
	protected static final String	URIGetterOutboundPortURI = "oport" ;
	
	// Emitter Component
	private static final String OUTBOUNDPORT_URI_PRESENCE = "opURIp";
	private static final String REGISTER_URI_PRESENCE = "rURIp";

	private static final String OUTBOUNDPORT_URI_WINDOW = "oURIw";
	private static final String INBOUNDPORT_URI_WINDOW = "ipRIw";
	private static final String REGISTER_URI_WINDOW = "rURIw";
	
	// Executor component inboundPort
	private static final String INBOUNDPORT_URI_ALARM = "iporta";
	
	// Correlator ports
	private static final String OUTBOUNDPORT_URI_INTRUSION = "opURIi";
	private static final String INBOUNDPORT_URI_INTRUSION = "ipURIi";
	private static final String REGISTER_URI_INTRUSION = "rURIi";

	// CepBus URIs
		protected static final String	PROVIDER_COMPONENT_URI = "my-URI-cepbus" ;
		protected static final String OUTBOUNDPORT_URI_CEPB = "cepoport";
		protected static final String INBOUNDPORT_URI_CEPB = "cepiport";


	public static final int LIFE_CYCLE_DURATION = 15000;
	
	protected String CEPBus;
	protected String PresenceDetector;
	protected String WindowController;
	protected String AlarmComponent;
	protected String IntrusionCorrelator;
	
	public MultiCVM(String[] args,int xLayout,int yLayout) 
	throws Exception
	{
		//super(args);
		super(args,xLayout, yLayout);
	}
	
	@Override
	public void	deploy() 
	throws Exception
	{	
		
		this.CEPBus = 
				AbstractComponent.createComponent(
				CEPBus.class.getCanonicalName(),
				new Object[] {});//PROVIDER_COMPONENT_URI,
						//INBOUNDPORT_URI_CEPB});
		assert	this.isDeployedComponent(this.CEPBus) ;

		
		this.PresenceDetector=
				AbstractComponent.createComponent(
				PresenceDetector.class.getCanonicalName(),
				new Object[] {OUTBOUNDPORT_URI_PRESENCE, REGISTER_URI_PRESENCE,
						LIFE_CYCLE_DURATION/1000, 1000, 1000, 42});

		assert	this.isDeployedComponent(this.PresenceDetector) ;

		
		this.WindowController = 
				AbstractComponent.createComponent(
				WindowController.class.getCanonicalName(),
				new Object[] {INBOUNDPORT_URI_WINDOW,OUTBOUNDPORT_URI_WINDOW,
						REGISTER_URI_WINDOW,
						LIFE_CYCLE_DURATION/1000, 1000, 1000, 401});
		assert	this.isDeployedComponent(this.WindowController) ;
		
		
		this.AlarmComponent = AbstractComponent.createComponent(
				AlarmComponent.class.getCanonicalName(),
				new Object[] {INBOUNDPORT_URI_ALARM});
		assert	this.isDeployedComponent(this.AlarmComponent) ;
		
		// Specify which uri port to listen in order to receive these events
		ArrayList<String> urisToListen = new ArrayList<>();
		urisToListen.add(OUTBOUNDPORT_URI_PRESENCE);
		urisToListen.add(OUTBOUNDPORT_URI_WINDOW);
		
		this.IntrusionCorrelator = AbstractComponent.createComponent(
				IntrusionCorrelator.class.getCanonicalName(),
				new Object[] {INBOUNDPORT_URI_ALARM,
						INBOUNDPORT_URI_INTRUSION,
						REGISTER_URI_INTRUSION, urisToListen});
		assert	this.isDeployedComponent(this.IntrusionCorrelator) ;
		
		super.deploy();	
	}
	
	/**
	 * Creation of components
	 */
	// on prends les methode pour creer les composants
	@Override
	public void			instantiateAndPublish() throws Exception
	{
		if (thisJVMURI.equals(PROVIDER_JVM_URI)) {//cours 5 slide 17
			// create the provider component
			this.uriProviderURI = AbstractComponent.createComponent(
					CEPBus.class.getCanonicalName(),
					new Object[] {});//PROVIDER_JVM_URI,INBOUNDPORT_URI_CEPB});

			assert	this.isDeployedComponent(this.uriProviderURI) ;
			// make it trace its operations; comment and uncomment the line to see
			// the difference
			this.toggleTracing(this.uriProviderURI) ;
			this.toggleLogging(this.uriProviderURI) ;
			assert	this.uriConsumerURI == null && this.uriProviderURI != null ;

		} else if (thisJVMURI.equals(CONSUMER_JVM_URI)) {
			// create the consumer component
			this.uriConsumerURI =
					AbstractComponent.createComponent(
					PresenceDetector.class.getCanonicalName(),
					new Object[] {OUTBOUNDPORT_URI_PRESENCE, REGISTER_URI_PRESENCE,
							LIFE_CYCLE_DURATION/1000, 1000, 1000, 407});
			assert	this.isDeployedComponent(this.uriConsumerURI) ;
			//emitter 1 arg=uri port de sorti
			
			// make it trace its operations; comment and uncomment the line to see
			// the difference
			this.toggleTracing(this.uriConsumerURI) ;
			this.toggleLogging(this.uriConsumerURI) ;
			assert	this.uriConsumerURI != null && this.uriProviderURI == null ;

		}else {
			System.out.println("Unknown JVM URI... " + thisJVMURI) ;
		}
		super.instantiateAndPublish();
	}

	/**
	 * static interconnection of ports
	 */
	//deploy de CVM=>dans interconnect
	@Override
	public void			interconnect() throws Exception
	{
		assert	this.isIntantiatedAndPublished() ;

		if (thisJVMURI.equals(PROVIDER_JVM_URI)) {

			assert	this.uriConsumerURI == null && this.uriProviderURI != null ;

		} else if (thisJVMURI.equals(CONSUMER_JVM_URI)) {

			assert	this.uriConsumerURI != null && this.uriProviderURI == null ;
			// do the connection
			this.doPortConnection(this.uriConsumerURI,URIGetterOutboundPortURI, INBOUNDPORT_URI_CEPB,
					CEPBusEventEmissionConnector.class.getCanonicalName());
			
		} else {

			System.out.println("Unknown JVM URI... " + thisJVMURI) ;

		}

		super.interconnect();
	}
	public static void	main(String[] args)
	{
		try {
			MultiCVM cvm = new MultiCVM(args,2,5) ;
			cvm.startStandardLifeCycle(LIFE_CYCLE_DURATION);
			Thread.sleep(2000L) ;
			System.exit(0) ;
		} catch (Exception e) {
			throw new RuntimeException(e) ;
		}
	}

}
